package com.ax.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ax.model.TickModel;

@Component
public class CustomTickerRepository {

	private final String url = "jdbc:postgresql://35.198.26.13:5432/postgres";
	private final String user = "postgres";
	private final String password = "tickerDB";

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public void Insert(List<TickModel> ticks) throws SQLException {
		String sql = "INSERT INTO public.tick\n"
				+ "(condicao_oferta_compra, condicao_oferta_venda, corretora_compra, corretora_venda, data_oferta_compra, data_oferta_venda, data_sessao, generation_id_compra, generation_id_venda, hora, indicador_anulacao, indicador_direto, numero_negocio, preco_negocio, quantidade, sequencia_oferta_compra, sequencia_oferta_venda, simbolo)\n"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);

		final int batchSize = 10000;
		int count = 0;

		for (TickModel t : ticks) {

			ps.setString(1, t.getCondicaoOfertaCompra());
			ps.setString(2, t.getCondicaoOfertaVenda());
			ps.setInt(3, t.getCorretoraCompra());
			ps.setInt(4, t.getCorretoraVenda());
			ps.setDate(5, new java.sql.Date (t.getDataOfertaCompra().getTime()));
			ps.setDate(6, new java.sql.Date (t.getDataOfertaVenda().getTime()));
			ps.setDate(7, new java.sql.Date (t.getDataSessao().getTime()));
			ps.setLong(8, t.getGenerationIdCompra());
			ps.setLong(9, t.getGenerationIdVenda());
			ps.setTimestamp(10, t.getHora());
			ps.setString(11, t.getIndicadorAnulacao());
			ps.setString(12, t.getIndicadorDireto());
			ps.setLong(13, t.getNumeroNegocio());
			ps.setBigDecimal(14, t.getPrecoNegocio());
			ps.setLong(15, (long) t.getQuantidade());
			ps.setLong(16, t.getSequenciaOfertaCompra());
			ps.setLong(17, t.getSequenciaOfertaVenda());
			ps.setString(18, t.getSimbolo());

			ps.addBatch();

			if (++count % batchSize == 0) {
				ps.executeBatch();
			}
		}
		ps.executeBatch(); 
		ps.close();
		connection.close();

	}

}
