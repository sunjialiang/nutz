package org.nutz.mvc.tree;

import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.mvc.Controllor;
import org.nutz.service.tree.TreeService;

public abstract class TreeControllor<T> implements Controllor {

	protected TreeControllor(TreeService<T> service) {
		this.service = service;
	}

	protected TreeService<T> service;

	public String charset;

	public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		T obj;
		try {
			obj = Json.fromJson(service.getEntityClass(), new InputStreamReader(request.getInputStream(),
					(charset == null ? "UTF-8" : charset)));
		} catch (Exception e) {
			try {
				long id = Long.valueOf(request.getParameter("id"));
				obj = service.fetch(id);
			} catch (Exception e1) {
				throw Lang.makeThrow("Fail to get ancestors, for the reason '%s'",
						"request did contains a valid json object or a valid object id!");
			}
		}
		return execute(obj);
	}

	protected abstract Object execute(T obj);

}