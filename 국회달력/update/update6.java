package update;

import java.sql.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class update6 {//영화 UPDATE--테스트완 성공 !!

	public void ud1() throws ClassNotFoundException {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");//드라이버
			String connectionUrl = "jdbc:mysql://13.231.175.154:3306/project2022";//DB 경로
			Connection con = DriverManager.getConnection(connectionUrl,"seyoungkim","Sy01033756858!");
			
			System.out.println("mysql 서버 접속에 성공하였습니다.");
			String sql = "INSERT INTO PERFORMANCE (ARTICLE_TITLE, RE_DT, DT, ETC_CHAR11, LINK_URL)"
			           +" VALUES (?,?,?,?,?)";

			String sql1="SELECT count(*) as COUNT FROM PERFORMANCE WHERE ARTICLE_TITLE=? AND DT=?";//행개수로 탐색할까?
			ResultSet rs=null;

			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			
			for (int page = 1; page < 2; page++) {//상위 30개만 검토 
				String pageNum = Integer.toString(page);
				StringBuilder urlBuilder = new StringBuilder(
						"https://open.assembly.go.kr/portal/openapi/nneiozjtawpqhaidq"); /* URL */
				urlBuilder.append("?" + URLEncoder.encode("KEY", "UTF-8")
						+ "=707babc7c5a14712bbf2cdb774011135"); /* Service Key */
				urlBuilder.append("&" + URLEncoder.encode("pIndex", "UTF-8") + "="
						+ URLEncoder.encode(pageNum, "UTF-8")); /* 페이지번호 */
				urlBuilder.append("&" + URLEncoder.encode("pSize", "UTF-8") + "="
						+ URLEncoder.encode("30", "UTF-8")); /* 한 페이지 결과 수 */

				urlBuilder.append("&" + URLEncoder.encode("Type", "UTF-8") + "="
						+ URLEncoder.encode("json", "UTF-8")); /* 응답데이터 형식(xml/json) default : xml */
				
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				System.out.println("Response code: " + conn.getResponseCode());

				BufferedReader rd;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				String line;
				String result = "";
				while ((line = rd.readLine()) != null) {
					result = result.concat(line);
				}
				System.out.println(result.toString());

				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(result);

				JSONArray parse_body = (JSONArray) obj.get("nneiozjtawpqhaidq");// 이부분 수정필요함
				//System.out.println(parse_body.toString());

				JSONObject header = (JSONObject) parse_body.get(1);
				JSONArray parse_listArr = (JSONArray) header.get("row");
				//System.out.println(parse_listArr.toString()); /// 리스트에 저장은 됐음
				//System.out.println(parse_listArr.size());// 3개의 정보까지는 담겼고

				if (parse_listArr.size() > 0) {
					for (int i = 0; i < parse_listArr.size(); i++) {
						pstmt = con.prepareStatement(sql);
						pstmt1 = con.prepareStatement(sql1);
						
						JSONObject infor = (JSONObject) parse_listArr.get(i);
						/* System.out.println(drug.toString()); */
						String ARTICLE_TITLE = (String) infor.get("ARTICLE_TITLE");
						pstmt.setString(1, ARTICLE_TITLE);// 제목
						pstmt1.setString(1,ARTICLE_TITLE);
						String RE_DT = (String) infor.get("RE_DT");// 관람예약
						pstmt.setString(2, RE_DT);
						String DT = (String) infor.get("DT");// 일시
						pstmt.setString(3, DT);
						pstmt1.setString(2,DT);
						String ETC_CHAR11 = (String) infor.get("ETC_CHAR11");// 장소
						pstmt.setString(4, ETC_CHAR11);
						String LINK_URL = (String) infor.get("LINK_URL");// 링크주소
						pstmt.setString(5, LINK_URL);

						rs = pstmt1.executeQuery();//select문 실행
						
						while(rs.next()) {
							String count=rs.getString("COUNT");
							System.out.println(count);
							if(count.equals("0")) {
								int resultsql = pstmt.executeUpdate();//같은 데이터가 존재하지 않으면 insert수행
								System.out.println("처리된 레코드 수" + resultsql);
									
							}
							else {//같은 데이터가 존재하면 다음 데이터로 넘어감
								continue;
							}
							
						}
						
						
					}

				}

				rd.close();
				conn.disconnect();
				/* System.out.println(sb.toString()); */

			}
			
			con.close();
			pstmt.close();
			pstmt1.close();
		} catch (SQLException e) {
			System.out.println(e + "=> Sql 예외 ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}