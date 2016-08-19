import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class ClaimsTest {
    public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
        ClaimDao claimDao = new ClaimDao();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> request = new HashMap<String, String>();
        request.put( "username", "WY20385" );
        request.put( "password", "C@nad@01" );
        request.put( "npi", "1497876965" );
        request.put( "region_code", "FISPH4-1" );
        String payload = new ObjectMapper().writeValueAsString(request);
        byte[] message = payload.getBytes("UTF-8");
        String encoded = DatatypeConverter.printBase64Binary(message);

        try {
            ProcessBuilder pb = new ProcessBuilder("/Users/challendy/Projects/mis/mis-operator/operator.sh", encoded);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNext() != false) {
                String json = scanner.nextLine();
                List<Claim> list = mapper.readValue(json, new TypeReference<List<Claim>>() {});
                for (Claim claim : list) {
                    claimDao.insertOrUpdate(claim);
                }
            }
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        claimDao.close();
    }
}
