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

public class update1 {//��ȸ�� UPDATE

	public void ud1() throws ClassNotFoundException {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");//����̹�
			String connectionUrl = "jdbc:mysql://13.231.175.154:3306/project2022";//DB ���
			Connection con = DriverManager.getConnection(connectionUrl,"seyoungkim","Sy01033756858!");
			//Statement stmt = con.createStatement();
			System.out.println("mysql ���� ���ӿ� �����Ͽ����ϴ�.");
			String sql = "INSERT INTO bonsche (MEETINGSESSION, CHA, TITLE, MEETING_DATE, MEETING_TIME, LINK_URL, "
			           +"UNIT_CD, UNIT_NM)"
			           +" VALUES (?,?,?,?,?,?,?,?)";
			String sql1="SELECT count(*) as COUNT FROM bonsche WHERE TITLE=? AND MEETING_DATE=?";//�ళ���� Ž���ұ�?
			ResultSet rs=null;

			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			
			for (int page = 1; page < 2; page++) {//�� 170�� ���� 30���� ���� 
				String pageNum = Integer.toString(page);
				StringBuilder urlBuilder = new StringBuilder(
						"https://open.assembly.go.kr/portal/openapi/nekcaiymatialqlxr"); /* URL */
				urlBuilder.append("?" + URLEncoder.encode("KEY", "UTF-8")
						+ "=707babc7c5a14712bbf2cdb774011135"); /* Service Key */
				urlBuilder.append("&" + URLEncoder.encode("pIndex", "UTF-8") + "="
						+ URLEncoder.encode(pageNum, "UTF-8")); /* ��������ȣ */
				urlBuilder.append("&" + URLEncoder.encode("pSize", "UTF-8") + "="
						+ URLEncoder.encode("100", "UTF-8")); /* �� ������ ��� �� */

				urlBuilder.append("&" + URLEncoder.encode("Type", "UTF-8") + "="
						+ URLEncoder.encode("json", "UTF-8")); /* ���䵥���� ����(xml/json) default : xml */
				urlBuilder.append(
						"&" + URLEncoder.encode("UNIT_CD", "UTF-8") + "=" + URLEncoder.encode("100021", "UTF-8"));// 21��
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

				JSONArray parse_body = (JSONArray) obj.get("nekcaiymatialqlxr");// �̺κ� �����ʿ���
				//System.out.println(parse_body.toString());

				JSONObject header = (JSONObject) parse_body.get(1);
				JSONArray parse_listArr = (JSONArray) header.get("row");
				//System.out.println(parse_listArr.toString()); /// ����Ʈ�� ������ ����
				//System.out.println(parse_listArr.size());// 3���� ���������� ����

				if (parse_listArr.size() > 0) {
					for (int i = 0; i < parse_listArr.size(); i++) {
						pstmt = con.prepareStatement(sql);
						pstmt1 = con.prepareStatement(sql1);
						JSONObject infor = (JSONObject) parse_listArr.get(i);
						/* System.out.println(drug.toString()); */
						String MEETINGSESSION = (String) infor.get("MEETINGSESSION");
						pstmt.setString(1, MEETINGSESSION);// ȸ��
						String CHA = (String) infor.get("CHA");// ����
						pstmt.setString(2, CHA);
						String TITLE = (String) infor.get("TITLE");// ����
						pstmt.setString(3, TITLE);
						pstmt1.setString(1,TITLE);
						String MEETTING_DATE = (String) infor.get("MEETTING_DATE");// ����
						pstmt.setString(4, MEETTING_DATE);
						pstmt1.setString(2,MEETTING_DATE);
						String MEETTING_TIME = (String) infor.get("MEETTING_TIME");// �Ͻ�
						pstmt.setString(5, MEETTING_TIME);
						String LINK_URL = (String) infor.get("LINK_URL");// ��ũ�ּ�
						pstmt.setString(6, LINK_URL);
						String UNIT_CD = (String) infor.get("UNIT_CD");// ���
						pstmt.setString(7, UNIT_CD);
						String UNIT_NM = (String) infor.get("UNIT_NM");// ���
						pstmt.setString(8, UNIT_NM);

						rs = pstmt1.executeQuery();//select�� ����
						
						while(rs.next()) {
							String count=rs.getString("COUNT");
							System.out.println(count);
							if(count.equals("0")) {
								int resultsql = pstmt.executeUpdate();//���� �����Ͱ� �������� ������ insert����
								System.out.println("ó���� ���ڵ� ��" + resultsql);
									
							}
							else {//���� �����Ͱ� �����ϸ� ���� �����ͷ� �Ѿ
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
			System.out.println(e + "=> Sql ���� ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}