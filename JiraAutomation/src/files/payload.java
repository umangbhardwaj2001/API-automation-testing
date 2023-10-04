package files;

public class payload {

	public static String credentials() {
		String username = "umangbhardwaj";
		String password = "9234";
		return "{\r\n"
				+ "    \"username\" : \""+username+"\",\r\n"
				+ "    \"password\" : \""+password+"\"\r\n"
				+ "}";
	}

	public static String issueBody() {
		// TODO Auto-generated method stub
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\":\"RSA\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"creating first issue\",\r\n"
				+ "        \"description\": \"description\",\r\n"
				+ "        \"issuetype\":{\r\n"
				+ "            \"name\":\"Bug\"\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "    }\r\n"
				+ "}";
	}

	public static String addCmnt() {
		// TODO Auto-generated method stub
		return "{\r\n"
				+ "    \"body\": \"Updated comment from REST API\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
	}

	public static String updateCmnt() {
		// TODO Auto-generated method stub
		return "{\r\n"
				+ "    \"body\": \"updating comment using REST API.\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
	}

}
