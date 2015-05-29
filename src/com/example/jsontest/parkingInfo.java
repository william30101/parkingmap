package com.example.jsontest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;




public class parkingInfo {

	private ArrayList<parkingInfo> parkinginfoArray = new ArrayList();
	
	String id = null;
	String area = null;
	String name = null;
	int type = 0;
	int type2 = 0;
	String summary = null;
	String address = null;
	String tel = null;
	String payex = null;
	String servicetime = null;
	double tw97x = 0;
	double tw97y = 0;
	int totalcar = 0;
	int totalmotor = 0;
	int totalbike = 0;
	double XWgs = 0;
	double YWgs = 0;
	int fareWorkingDay0008 = 0 ;
	int fareWorkingDay0818 = 0;
	int fareWorkingDay1824 = 0;
	int fareHoliDay0008 = 0;
	int fareHoliDay0818 = 0;
	int fareHoliDay1824 = 0;
	
	public parkingInfo parkingInfoCreate(JSONObject inputJson)
	{
		try {
			if (!inputJson.isNull("id"))
				this.id = inputJson.getString("id");
			
			if (!inputJson.isNull("area"))
				this.area = inputJson.getString("area");
			
			if (!inputJson.isNull("name"))
				this.name = inputJson.getString("name");
			
			
			if (!inputJson.isNull("type"))
				this.type = Integer.valueOf(inputJson.getString("type"));
			
			
			if (!inputJson.isNull("type2"))
				this.type2 = Integer.valueOf(inputJson.getString("type2"));
			
			if (!inputJson.isNull("summary"))
				this.summary = inputJson.getString("summary");
			
			if (!inputJson.isNull("address"))
				this.address = inputJson.getString("address");
			
			if (!inputJson.isNull("tel"))
				this.tel = inputJson.getString("tel");
			
			if (!inputJson.isNull("payex"))
				this.payex = inputJson.getString("payex");
			
			if (!inputJson.isNull("servicetime"))
				this.servicetime = inputJson.getString("servicetime");
			
			if (!inputJson.isNull("tw97x"))
				this.tw97x = Double.parseDouble(inputJson.getString("tw97x"));
			
			if (!inputJson.isNull("tw97y"))
				this.tw97y = Double.parseDouble(inputJson.getString("tw97y"));
			
			if (!inputJson.isNull("totalcar"))
				this.totalcar = Integer.valueOf(inputJson.getString("totalcar"));
			
			if (!inputJson.isNull("totalmotor"))
				this.totalmotor = Integer.valueOf(inputJson.getString("totalmotor"));
			
			if (!inputJson.isNull("totalbike"))
				this.totalbike = Integer.valueOf(inputJson.getString("totalbike"));
			
			if (this.tw97x != 0 && this.tw97y != 0)
			{
				double latlon[] = TWD97TM2toWGS84(this.tw97x,this.tw97y);
				this.XWgs = latlon[0];
				this.YWgs = latlon[1];
			}
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	public double[] TWD97TM2toWGS84(double x, double y) {
	    // Ref1 : http://www.uwgb.edu/dutchs/UsefulData/UTMFormulas.htm
	    // Ref2 : http://blog.ez2learn.com/2009/08/15/lat-lon-to-twd97/
	    // 修正 Ref2 中lng回傳值 toDegree 設錯地方的Bug
	   
	    double dx = 250000;
	    double dy = 0;
	    double lon0 = 121;
	    double k0 = 0.9999;
	    double a =  6378137.0;
	    double b = 6356752.314245;
	    double e = Math.sqrt((1-(b*b)/(a*a)));
	   
	    x -= dx;
	    y -= dy;
	 
	    // Calculate the Meridional Arc
	    double M = y/k0;
	 
	    // Calculate Footprint Latitude
	    double mu = M/(a*(1.0 - Math.pow(e, 2)/4.0 - 3*Math.pow(e, 4)/64.0 - 5*Math.pow(e, 6)/256.0));
	    double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));
	 
	    double J1 = (3*e1/2 - 27*Math.pow(e1, 3)/32.0);
	    double J2 = (21*Math.pow(e1, 2)/16 - 55*Math.pow(e1, 4)/32.0);
	    double J3 = (151*Math.pow(e1, 3)/96.0);
	    double J4 = (1097*Math.pow(e1, 4)/512.0);
	 
	    double fp = mu + J1*Math.sin(2*mu) + J2*Math.sin(4*mu) + J3*Math.sin(6*mu) + J4*Math.sin(8*mu);
	 
	    // Calculate Latitude and Longitude
	 
	    double e2 = Math.pow((e*a/b), 2);
	    double C1 = Math.pow(e2*Math.cos(fp), 2);
	    double T1 = Math.pow(Math.tan(fp), 2);
	    double R1 = a*(1-Math.pow(e, 2))/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), (3.0/2.0));
	    double N1 = a/Math.pow((1-Math.pow(e, 2)*Math.pow(Math.sin(fp), 2)), 0.5);
	 
	    double D = x/(N1*k0);
	    //double drad = Math.PI/180.0;
	   
	    // lat
	    double Q1 = N1*Math.tan(fp)/R1;
	    double Q2 = (Math.pow(D, 2)/2.0);
	    double Q3 = (5 + 3*T1 + 10*C1 - 4*Math.pow(C1, 2) - 9*e2)*Math.pow(D, 4)/24.0;
	    double Q4 = (61 + 90*T1 + 298*C1 + 45*Math.pow(T1, 2) - 3*Math.pow(C1, 2) - 252*e2)*Math.pow(D, 6)/720.0;
	    double lat = Math.toDegrees(fp - Q1*(Q2 - Q3 + Q4));
	 
	    // long
	    double Q5 = D;
	    double Q6 = (1 + 2*T1 + C1)*Math.pow(D, 3)/6.0;
	    double Q7 = (5 - 2*C1 + 28*T1 - 3*Math.pow(C1, 2) + 8*Math.pow(e2,2) + 24*Math.pow(T1, 2))*Math.pow(D, 5)/120.0;
	   
	    double lon = lon0 + Math.toDegrees((Q5 - Q6 + Q7)/Math.cos(fp)); 
	   
	    return new double[] {lat, lon};
	   } 
	
	public String getJson(String url) { // 這邊所接進來的url是撈JSON的那個網址
		// 宣告一個String來存放等等撈到的資料
		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			Log.i("william","execute done");
			HttpEntity entity = response.getEntity();
			Log.i("william","getentity done");
			InputStream is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "big5"), 9999999);
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void parseJson() {
		// 宣告字串json並呼叫剛剛寫好的副程式getJson()來抓取json字串
		
		
		
		String json = getJson("http://opendata.dot.taipei.gov.tw/opendata/alldescriptions.json");
		// 由圖片可以知道，字串一開始是{}也就是物件，因此宣告一個JSON物件；
		// 反之若一開始是[]陣列則宣告JSON陣列。
		//Log.i("william","json string is " + json);
		JSONObject obj;
		try {

			
			obj = new JSONObject(json);
			// 宣告字串data來存放剛剛撈到的字串，剛剛的物件叫obj因此對他下obj.getString(“data”)，
			// 而裡面的data則是因為在上圖中最外層的物件裡包的JSON陣列叫做data。
			String data = obj.getString("data");
			JSONObject parkobject = new JSONObject(data);
			String parkingData = parkobject.getString("park");
			// 這裡是宣告我們要繼續拆解的JSON陣列，所以要把剛剛撈到的字串轉換為JSON陣列
			JSONArray parkingDataArray = new JSONArray(parkingData);
			// 先宣告name跟id的字串陣列來存放等等要拆解的資料

			// 因為data陣列裡面有好多個JSON物件，因此利用for迴圈來將資料抓取出來
			// 因為不知道data陣列裡有多少物件，因此我們用.length()這個方法來取得物件的數量
			for (int i = 0; i < parkingDataArray.length(); i++) {
				// 接下來這兩行在做同一件事情，就是把剛剛JSON陣列裡的物件抓取出來
				// 並取得裡面的字串資料
				// dataArray.getJSONObject(i)這段是在講抓取data陣列裡的第i個JSON物件
				// 抓取到JSON物件之後再利用.getString(“欄位名稱”)來取得該項value
				// 這裡的欄位名稱就是剛剛前面所提到的name:value的name
				
				parkinginfoArray.add(new parkingInfo().parkingInfoCreate(parkingDataArray.getJSONObject(i)));

				Log.i("william","parking name= " + parkinginfoArray.get(i).name + "\r\n " +
						"id=" + parkinginfoArray.get(i).id + " x = " +parkinginfoArray.get(i).XWgs
						+ " Y = " +parkinginfoArray.get(i).YWgs);
				

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	
}
