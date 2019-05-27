package com.ax.service.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ax.model.TickModel;
import com.ax.repository.CustomTickerRepository;

@Component
public class CsvService {
	@Autowired
	CustomTickerRepository customRepository;
	List<TickModel> ticks;

	public void ProcessFile() throws IOException {

		String mainPath = "/Users/victor.vilella/zup/pessoal/ticks";
		File file = new File(mainPath);
		String[] files = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				// return new File(current, name).isDirectory();
				return name.contains(".zip");
			}
		});
		System.out.println(Arrays.toString(files));

		for (Iterator<String> i = Arrays.asList(files).iterator(); i.hasNext();) {

			String fileName = i.next();
			Path zipPath = Paths.get(mainPath, fileName);
			Path txtPath = Paths.get(mainPath, fileName.replace(".zip", ".txt"));

			unzip(zipPath.toString(), mainPath);

			if (txtPath.toFile().exists()) {
				System.out.println("Start: " + new Date());
				processFile(txtPath);
				saveTicks();
				Files.delete(txtPath);
				moveProcessedFile(zipPath);
				System.out.println("End: " + new Date());

			}
		}
	}

	private void saveTicks() {
		try {
			customRepository.Insert(ticks);
			ticks.clear();
			ticks = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void moveProcessedFile(Path zipPath) throws IOException {

		Path processedPath = Paths.get(zipPath.toFile().getParent(), "processed");
		if (!processedPath.toFile().exists()) {
			Files.createDirectory(processedPath);
		}

		Files.move(zipPath, Paths.get(processedPath.toString(), zipPath.getFileName().toString()),
				StandardCopyOption.REPLACE_EXISTING);

	}

	private void processFile(Path txtFile) throws IOException {
		try (Stream<String> stream = Files.lines(txtFile)) {
			stream.forEach(t -> {
				try {
					processLine(t);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

	}

	private static void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processLine(String str) throws ParseException {

		if (!str.startsWith("R")) {
			TickModel tick = new TickModel();
			String[] values = str.split(";");

			tick.setDataSessao((Date) new SimpleDateFormat("yyyy-MM-dd").parse(values[0]));
			tick.setSimbolo(values[1].trim());
			tick.setNumeroNegocio(Long.parseLong(values[2]));
			tick.setPrecoNegocio(BigDecimal.valueOf((Double.parseDouble(values[3]))));
			tick.setQuantidade(Long.valueOf(values[4]));
			tick.setHora(Timestamp.valueOf(values[0] + " " + values[5]));
			tick.setIndicadorAnulacao(values[6]);
			tick.setDataOfertaCompra((Date) new SimpleDateFormat("yyyy-MM-dd").parse(values[7]));
			tick.setSequenciaOfertaCompra(Long.parseLong(values[8]));
			tick.setGenerationIdCompra(Long.parseLong(values[9]));
			tick.setCondicaoOfertaCompra(values[10]);
			tick.setDataOfertaVenda((Date) new SimpleDateFormat("yyyy-MM-dd").parse(values[11]));
			tick.setSequenciaOfertaVenda(Long.parseLong(values[12]));
			tick.setGenerationIdVenda(Long.parseLong(values[13]));
			tick.setCondicaoOfertaVenda(values[14]);
			tick.setIndicadorDireto(values[15]);
			tick.setCorretoraCompra(Integer.parseInt(values[16]));
			tick.setCorretoraVenda(Integer.parseInt(values[17]));

			if (ticks == null) {
				ticks = new ArrayList<TickModel>();
			}

			ticks.add(tick);

			if (ticks.size() >= 300000) {
				saveTicks();
			}
		}
	}
}
