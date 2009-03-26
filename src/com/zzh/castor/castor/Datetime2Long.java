package com.zzh.castor.castor;

import java.util.Date;

import com.zzh.castor.Castor;

public class Datetime2Long extends Castor<java.util.Date, Long> {

	@Override
	protected Long cast(Date src, Class<?> toType, String... args) {
		return src.getTime();
	}

}