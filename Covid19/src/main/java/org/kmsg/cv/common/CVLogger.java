package org.kmsg.cv.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CVLogger {
	public static final Logger logger 			= LoggerFactory.getLogger("debug.MISlogs");
	public static final Logger errorLogs 		= LoggerFactory.getLogger("error.MISlogs");
	public static final Logger infoLogs     	= LoggerFactory.getLogger("info.MISlogs");
	public static final Logger loginLogs     	= LoggerFactory.getLogger("login.MISlogs");
	public static final Logger monthFreezeLogs  = LoggerFactory.getLogger("monthfreeze.MISlogs");
	public static final Logger bidsProcessLogs  = LoggerFactory.getLogger("bidsProcess.MISlogs");
}
