package com.study.app.common;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommonUtil {

	public static final String SEPARATOR = "/";
	
	private static final String[] ACCEPT_FORMATS = { "jpg", "jpeg" };
	
	public static boolean isNull( Object obj ) {
		return obj == null;
	}
	
	public static boolean isEmpty( Object obj ){
		if( obj == null ){
			return true;
		}
		if( obj instanceof String ){
			return "".equals( ( (String)obj ).trim() );
		}
		if( obj instanceof Integer ){
			return false;
		}
		return false;
	}
	
	public static boolean isEmptyList( List<?> list ) {
		if( list == null ) {
			return true;
		}
		return list.isEmpty();
	}
	
	public static boolean isEmptyMap( Map<?, ?> map ) {
		if( map == null ) {
			return true;
		}
		return map.isEmpty();
	}
	
	public static boolean isEmptyArray( Object[] os ) {
		if( os == null || os.length == 0 ) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyArray( JSONArray arr ) {
		if( arr == null || arr.size() == 0 ) {
			return true;
		}
		return false;
	}
	
	public static String getString( Object o ) {
		if( o == null ) {
			return "";
		}
		
		return ( String.valueOf( o ) ).trim();
	}
	
	public static Long getLong( Object o ) {
		if( o == null ) {
			return 0L;
		}else if( o instanceof Double ) {
			return (long)( (Double)o * 1 );
		}else if( o instanceof Long ) {
			return (Long)o;
		}else if( o instanceof Integer ) {
			return (long)(Integer)o;
		}else if( o instanceof String ) {
			if( isEmpty( (String)o ) ) {
				return 0L;
			}
			return Long.parseLong( (String)o );
		}else {
			return 0L;
		}
	}
	
	public static Double getDouble( Object o ) {
		if( o == null ) {
			return 0.0D;
		}else if( o instanceof Double ) {
			return (Double)o;
		}else if( o instanceof Long ) {
			return Double.parseDouble( (Long)o + "" );
		}else if( o instanceof Integer ) {
			return Double.parseDouble( (Integer)o + "" );
		}else if( o instanceof String ) {
			if( isEmpty( (String)o ) ) {
				return 0.0D;
			}
			return Double.parseDouble( (String)o );
		}else {
			return 0.0D;
		}
	}
	
	public static Float getFloat( Object o ) {
		if( o == null ) {
			return 0.0F;
		}else if( o instanceof Double ) {
			return Float.parseFloat( String.valueOf( o ) );
		}else if( o instanceof Long ) {
			return Float.parseFloat( (Long)o + "" );
		}else if( o instanceof Integer ) {
			return Float.parseFloat( (Integer)o + "" );
		}else if( o instanceof String ) {
			if( isEmpty( (String)o ) ) {
				return 0.0F;
			}
			return Float.parseFloat( (String)o );
		}else {
			return 0.0F;
		}
	}
	
	public static Integer getInteger( Object o ) {
		if( o == null ) {
			return 0;
		}else if( o instanceof Double ) {
			return (int)( (Double)o * 1 );
		}else if( o instanceof Float ) {
			return (int)( (Float)o * 1 );
		}else if( o instanceof Integer ) {
			return (Integer)o;
		}else if( o instanceof Long ) {
			return Integer.parseInt( (Long)o + "" );
		}else if( o instanceof String ) {
			if( isEmpty( (String)o ) ) {
				return 0;
			}
			return Integer.parseInt( (String)o );
		}else {
			return 0;
		}
	}
	
	public static boolean getBoolean( Object o ) {
		if( o == null ) {
			return false;
		}else if( o instanceof Boolean ) {
			return Boolean.parseBoolean( String.valueOf( o ) );
		}else {
			return false;
		}
	}
	
	public static JSONObject getJSONObject( JSONObject o, String key ) {
		if( o.get( key ) == null ) {
			return new JSONObject();
		}else {
			return (JSONObject)o.get( key );
		}
	}
	
	public static JSONArray getJSONArray( JSONObject o, String key ) {
		if( o.get( key ) == null ) {
			return new JSONArray();
		}else {
			return (JSONArray)o.get( key );
		}
	}
	
	public static String lPad( String base_str, int len, String asta ){
		return lrPad( base_str, len, asta, true );
	}
	
	public static String rPad( String base_str, int len, String asta ){
		return lrPad( base_str, len, asta, false );
	}
	
	public static String set1000Comma( Object o ){
		if( o == null ) {
			return "0";
		}else{
			return set1000CommaAlgorithm( String.valueOf( o ) );
		}
	}
	
	public static boolean isAcceptedUploadFileFormat( String format ) {
		for( String accept : ACCEPT_FORMATS ) {
			if( accept.equalsIgnoreCase( format ) ) {
				return true;
			}
		}
		return false;
	}
	
	public static String getServerTime( String f ){
		Calendar cal = Calendar.getInstance();
		int Y = cal.get(Calendar.YEAR);
		int M = cal.get(Calendar.MONTH)+1;
		int D = cal.get(Calendar.DATE);
		int H = cal.get(Calendar.HOUR_OF_DAY);
		int h = cal.get(Calendar.HOUR);
		int m = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);
		return f.replaceAll( "%Y", Y + "" ).replaceAll( "%m", ( M < 10 ? "0" + M : M + "" ) ).replaceAll( "%d", ( D < 10 ? "0" + D : D + "" ) )
			.replaceAll( "%H", ( H < 10 ? "0" + H : H + "" ) ).replaceAll( "%h", ( h < 10 ? "0" + h : h + "" ) )
			.replaceAll( "%i", ( m < 10 ? "0" + m : m + "" ) ).replaceAll( "%s", ( s < 10 ? "0" + s : s + "" ) );
	}
	
	public static void makeDirs( String path ) {
		File d = new File( path );
		d.mkdirs();
	}
	
	/**
	 * 랜덤알파벳 생성
	 * -1: 대소문자숫자혼합, 0:대소문자혼합, 1:대문자만, 2:소문자만, 3:숫자만
	 * @param t
	 * @return
	 */
	public static String getRandomString( int t, int l ){
		String _s = "";
		while( _s.length() < l ){
			_s += getRandomChar( t );
		}
		return _s;
	}
	
	private static String lrPad( String base_str, int len, String asta, boolean isLeft ){
		if( base_str == null ){
			return null;
		}
		if( len < 1 ){
			return "";
		}
		if( base_str.length() > len ){
			return isLeft ? base_str.substring( base_str.length() - len, base_str.length() ) : base_str.substring( 0, len );
		}
		if( base_str.length() == len ){
			return base_str;
		}
		while( base_str.length() < len ){
			base_str = isLeft ? asta + base_str : base_str + asta;
		}
		return base_str;
	}
	
	private static String set1000CommaAlgorithm( String str ){
		str = str.replace(",", "");
		boolean isMinus = false;
		if( Long.parseLong( str ) < 0L ){
			isMinus = true;
			str = str.replace("-", "");
		}
		String result = "";
		while( str.length() > 3 ){
			result = str.substring( str.length() -3, str.length()) + ( result.length() > 0 ? "," + result : result ) ;
			str = str.substring( 0, str.length() -3 );
		}
		if( str.length() > 0 ){
			result = str + ( result.length() > 0 ? "," + result : result ); 
		}
		return isMinus ? "-" + result : result;
	}
	
	private static char getRandomChar( int t ){
		Random r = new Random();
		char _c = '0';
		switch( t ){
			case -1 :
				int c = r.nextInt( 3 );
				if( c == 0 ){
					_c = String.valueOf( r.nextInt( 10 ) ).charAt( 0 );
				}else if( c == 1 ){
					_c = (char)( r.nextInt( 26 ) + 65 );
				}else {
					_c = (char)( r.nextInt( 26 ) + 97 );
				}
				break;
			case 0 :
				if( r.nextInt( 2 ) == 0 ){
					_c = (char)( r.nextInt( 26 ) + 65 );
				}else{
					_c = (char)( r.nextInt( 26 ) + 97 );
				}
				break;
			case 1 :
				_c = (char)( r.nextInt( 26 ) + 65 );
				break;
			case 2 :
				_c = (char)( r.nextInt( 26 ) + 97 );
				break;
			case 3 :
				_c = String.valueOf( r.nextInt( 10 ) ).charAt( 0 );
				break;
		}
		return _c;
	}
}