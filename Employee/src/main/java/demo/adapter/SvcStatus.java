package demo.adapter;

import java.util.HashMap;
import java.util.Map;

public class SvcStatus {
		public static final String STATUS 	= "SvcStatus";
		public static final String MSG	 	= "SvcMsg" ;
		public static final String SUCCESS 	= "Success";
		public static final String FAILURE 	= "Failure" ;
//		public static final String MESSAGE 	= "SvcMessage" ;
//		public static final String FAIL 	= SvcStatus.FAILURE ;
		
		public static final Map<String, Object> GET_FAILURE(final String msg){
			Map<String, Object> r = new HashMap<>();
			r.put(STATUS, FAILURE);
			r.put(MSG, msg);
			return r;
		}

		public static final Map<String, Object> GET_SUCCESS(final String msg){
			Map<String, Object> r = new HashMap<>();
			r.put(STATUS, SUCCESS);
			r.put(MSG, msg);
			return r;
		}

	}



