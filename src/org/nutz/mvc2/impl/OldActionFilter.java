package org.nutz.mvc2.impl;

import java.lang.reflect.Method;

import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc2.ActionFilterChain;

public class OldActionFilter extends AbstractActionFilter {

	@Override
	public void filter(ActionFilterChain chain) throws Throwable {
		ActionFilter[] filters = (ActionFilter[]) chain.get(ActionFilters.oldActionFilters);
		if (null != filters)
			for (ActionFilter filter : filters) {
				View view = filter.match(getServletContext(chain), 
						getRequest(chain), (Method)chain.get(ActionFilters.method));
				if (null != view) {
					chain.put(ActionFilters.returnValue, view);
					return;
				}
			}
		chain.doChain();
	}
	
}
