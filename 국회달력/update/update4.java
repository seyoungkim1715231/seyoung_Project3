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

public class update4 {//��ûȸ UPDATE--�׽�Ʈ�� ���� !!

	public void ud1() throws ClassNotFoundException {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");//����̹�
			String connectionUrl = "jdbc:mysql://13.231.175.154:3306/project2022";//DB ���
			Connection con = DriverManager.getConnection(connectionUrl,"seyoungkim","Sy01033756858!");
			//Statement stmt = con.createStatement();
			System.out.println("mysql ���� ���ӿ� �����Ͽ����ϴ�.");
			String sql = "INSERT INTO COMMSCHE_KONG (MEETING_DATE, MEETING_TIME, SESS, TITLE, COMMITTEE_NAME, LINK_URL2, "
			           +"UNIT_CD, UNIT_NM, HR_DEPT_CD)"
			           +" VALUES (?,?,?,?,?,?,?,?,?)";
			String sql1="SELECT count(*) as COUNT FROM COMMSCHE_KONG WHERE TITLE=? AND MEETING_DATE=?";//�ళ���� Ž���ұ�?
			ResultSet rs=null;

			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			
			for (int page = 1; page < 2; page++) {//���� 30���� ���� 
				String pageNum = Integer.toString(page);
				StringBuilder urlBuilder = new StringBuilder(
						"https://open.assembly.go.kr/portal/openapi/nhedurlwawoquyxwn"); /* URL */
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

				JSONArray parse_body = (JSONArray) obj.get("nhedurlwawoquyxwn");// �̺κ� �����ʿ���
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
						String MEETING_DATE = (String) infor.get("MEETING_DATE");
						String tmp = MEETING_DATE.replace(".","-");
						pstmt.setString(1, tmp);// ����
						pstmt1.setString(2,tmp);
						String MEETING_TIME = (String) infor.get("MEETING_TIME");//�Ͻ�
						pstmt.setString(2, MEETING_TIME);
						String SESS = (String) infor.get("SESS");// 
						pstmt.setString(3, SESS);
						String TITLE = (String) infor.get("DEGREE")+" "+(String) infor.get("TITLE");// ����
						//System.out.println(TITLE);
						pstmt.setString(4, TITLE);
						pstmt1.setString(1,TITLE);
						String COMMITTEE_NAME = (String) infor.get("COMMITTEE_NAME");//
						pstmt.setString(5, COMMITTEE_NAME);
						String LINK_URL2 = (String) infor.get("LINK_URL2");// ��ũ�ּ�
						pstmt.setString(6, LINK_URL2);
						String UNIT_CD = (String) infor.get("UNIT_CD");// ���
						pstmt.setString(7, UNIT_CD);
						String UNIT_NM = (String) infor.get("UNIT_NM");// ���
						pstmt.setString(8, UNIT_NM);
						String HR_DEPT_CD = (String) infor.get("HR_DEPT_CD");// ����ȸ�ڵ�
						pstmt.setString(9, HR_DEPT_CD);

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