package com.wedding.framework.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.emoldino.api.common.resource.base.log.util.LogUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TranUtils {

	public static <T> T doTran(Closure<T> closure) {
		return BeanUtils.get(TranUtils.class)._doTran(closure);
	}

	public static void doTran(ClosureNoReturn closure) {
		BeanUtils.get(TranUtils.class)._doTran(() -> {
			closure.execute();
			return null;
		});
	}

	@Transactional
	public <T> T _doTran(Closure<T> closure) {
		return closure.execute();
	}

	public static <T> T doNewTran(Closure<T> closure) {
		return BeanUtils.get(TranUtils.class)._doNewTran(closure);
	}

	public static void doNewTran(ClosureNoReturn closure) {
		BeanUtils.get(TranUtils.class)._doNewTran(() -> {
			closure.execute();
			return null;
		});
	}

	public static void doNewTranQuietly(ClosureNoReturn closure) {
		try {
			doNewTran(closure);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			//LogUtils.saveErrorQuietly(LogUtils.getErrorType(e), "DB_TRAN_FAIL", HttpUtils.getStatus(e, null), "DB Transaction Fail at " + closure.getClass().getName(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T _doNewTran(Closure<T> closure) {
		return closure.execute();
	}

	public static <T> T doNeverTran(Closure<T> closure) {
		return BeanUtils.get(TranUtils.class)._doNeverTran(closure);
	}

	public static void doNeverTran(ClosureNoReturn closure) {
		BeanUtils.get(TranUtils.class)._doNeverTran(() -> {
			closure.execute();
			return null;
		});
	}

	@Transactional(propagation = Propagation.NEVER)
	public <T> T _doNeverTran(Closure<T> closure) {
		return closure.execute();
	}

}
