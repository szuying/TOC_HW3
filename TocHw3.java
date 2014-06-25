import org.json.*;
import java.net.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TocHw3 {
	public static void main(String[] args) throws Exception {
		
		/*
		String url="http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
		String dis_patt= "大安區";
		String road_patt= ".*復興南路.*";
		int year=103;
		*/
		
		String url=args[0];
		String dis_patt= args[1];
		String road_patt= ".*"+args[2]+".*";
		int year= Integer.parseInt(args[3]);
		
		Pattern dis_pattern = Pattern.compile(dis_patt);
		Pattern road_pattern = Pattern.compile(road_patt);
		
		URL data_url = new URL(url);
		URLConnection url_con = data_url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url_con.getInputStream(), "UTF-8"));
		String inputLine;
		int totalprice=0;
		int datacount=0;
		int line=0;
		while ((inputLine = in.readLine()) != null)
		{
			if(line!=0 && inputLine.charAt(0)=='{') {
				JSONObject jsonObj = new JSONObject(inputLine);
				String district = jsonObj.getString("鄉鎮市區");
				String road = jsonObj.getString("土地區段位置或建物區門牌");
				int date = jsonObj.getInt("交易年月");
				int price = jsonObj.getInt("總價元");
				//System.out.println(district+" "+road+" "+date+" "+price);
				
				Matcher dis_matcher = dis_pattern.matcher(district);
				Matcher road_matcher = road_pattern.matcher(road);
				if(dis_matcher.find()) {
					if(road_matcher.find()) {
						if(date>year*100)
						{
							totalprice+=price;
							datacount++;
							//System.out.println(district+" "+road+" "+date+" "+price);
						}
					}
				}
			}
			line++;
		}
		System.out.println(totalprice/datacount);
		in.close();
	}
}
