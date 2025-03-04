package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DolarData implements AutoCloseable{

	private static final String BASE_HISTORIC = "https://api.argentinadatos.com/v1/cotizaciones/dolares/blue/";// /{casa}/{fecha}
	private static final String ACTUAL = "https://dolarapi.com/v1/dolares/blue";
	
	HttpURLConnection con;
	
	public DolarData() {
	
	}
	
	public BigDecimal getActualValue() {
		
		JSONObject obj;
		BigDecimal venta = new BigDecimal(0);
		try {
			obj = new JSONObject(getJSONString(ACTUAL));
			venta = obj.getBigDecimal("venta");
			//double compra = obj.getDouble("compra");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        

        return venta;
	}

	public BigDecimal getHistoricalValues(LocalDate date) throws JSONException, IOException{
		
		JSONObject obj = new JSONObject(getJSONString(BASE_HISTORIC + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))); 
        
		BigDecimal venta = obj.getBigDecimal("venta");
        double compra = obj.getDouble("compra");

        return venta;
	}
	
	public Map<LocalDate, BigDecimal> getHistoricalValues() {
		Map<LocalDate, BigDecimal> values = new HashMap<LocalDate, BigDecimal>();
		try {
		JSONArray jsonArray = new JSONArray(getJSONString(BASE_HISTORIC));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            
            String date = obj.getString("fecha");
            BigDecimal venta = obj.getBigDecimal("venta");
            double compra = obj.getDouble("compra");
            
            values.put(LocalDate.parse(date.subSequence(0, 10)), venta);
        } 
		}catch(IOException e) {
			e.printStackTrace();
			values = null;
		}
		return values;
	}
	
	private String getJSONString(String url) throws IOException {
		URL uri = new URL(url);
		
		con = (HttpURLConnection) uri.openConnection();
		con.setRequestMethod("GET");
		
		if(con.getResponseCode() != 200) 
			throw new RuntimeException("Error al conectarse a la api dolar");

		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()),8192);
		StringBuilder sb = new StringBuilder();
		String linea;

		sb.append(reader.readLine());
		
		if(url.equalsIgnoreCase(BASE_HISTORIC)) {
			long objectSize = 89;//88 con un digito
			reader.skip(objectSize * 365 * 12 + 38);
		}

		char[] buffer = new char[8192];
		int numRead;
		
	      while ((numRead = reader.read(buffer)) != -1) {
	            sb.append(buffer, 0, numRead);
	        }

		//while((linea = reader.readLine()) != null) 
			//sb.append(linea);
	      //System.out.println(sb.toString());

	    reader.close();
		
		return sb.toString();
	}
	
	@Override
	public void close() throws Exception {
		con.disconnect();
	}
	
	
}
