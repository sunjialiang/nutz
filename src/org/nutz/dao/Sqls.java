package org.nutz.dao;

import java.util.List;

import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.DefaultStatementAdapter;
import org.nutz.dao.sql.FetchEntityCallback;
import org.nutz.dao.sql.FetchIntegerCallback;
import org.nutz.dao.sql.QueryEntityCallback;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.sql.SqlImpl;
import org.nutz.dao.sql.SqlLiteral;
import org.nutz.dao.tools.DTable;
import org.nutz.dao.tools.DTableParser;
import org.nutz.dao.tools.TableSqlMaker;
import org.nutz.dao.tools.impl.NutDTableParser;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;

public class Sqls {

	private static DefaultStatementAdapter ADAPTER = new DefaultStatementAdapter();

	public static Sql create(String sql) {
		return create(new SqlLiteral().valueOf(sql));
	}

	public static Sql create(SqlLiteral sql) {
		return new SqlImpl(sql, ADAPTER);
	}

	public static Sql fetchEntity(String sql) {
		return create(sql).setCallback(callback.fetchEntity());
	}

	public static Sql fetchInt(String sql) {
		return create(sql).setCallback(callback.integer());
	}

	public static Sql queryEntity(String sql) {
		return create(sql).setCallback(callback.queryEntity());
	}

	public static __ callback = new __();

	public static class __ {
		public SqlCallback fetchEntity() {
			return new FetchEntityCallback();
		}

		public SqlCallback integer() {
			return new FetchIntegerCallback();
		}

		public SqlCallback queryEntity() {
			return new QueryEntityCallback();
		}
	}

	public static void execute(Dao dao, String sqls) {
		DTableParser parser = new NutDTableParser();
		TableSqlMaker maker = TableSqlMaker.newInstance(((NutDao) dao).meta());
		List<DTable> dts = parser.parse(sqls);
		for (DTable dt : dts) {
			Sql c = maker.makeCreateSql(dt);
			Sql d = maker.makeDropSql(dt);
			if (dao.exists(dt.getName()))
				dao.execute(d, c);
			else
				dao.execute(c);
		}
	}

	public static void executeFile(Dao dao, String path) {
		String sqls = Lang.readAll(Streams.fileInr(path));
		Sqls.execute(dao, sqls);
	}

}