package idv.ktw.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
    	try {
    		String inputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\big_lottery_invalid.json";
    		String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\big_lottery_result.json";
    		
    		Map<String, Object> resultUnmarshall = unmarshall(inputPath);
    		List<RewardResult> resultCheckReward = checkReward(resultUnmarshall);
    		marshall(outputPath, resultCheckReward);
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private static Map<String, Object> unmarshall(String path) throws IOException{
    	ObjectMapper objectMapper = new ObjectMapper();
    	
		JsonNode jsonNode = objectMapper.readValue(new File(path), JsonNode.class);
		JsonNode myNumbers = jsonNode.get("myNumbers");
		JsonNode lotteryNumbers = jsonNode.get("lotteryNumbers");
		
		List<MyNumber> listMyNumbers = new ArrayList<MyNumber>();
		List<LotteryNumber> listLotteryNumbers = new ArrayList<LotteryNumber>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (myNumbers.isArray()) {
			for(JsonNode n: myNumbers) {
				MyNumber temp = new MyNumber(n);
				// System.out.printf("%s: %s%n", temp.number, temp.getClass().getName());
				listMyNumbers.add(temp);
			}
		}
		
		if (lotteryNumbers.isArray()) {
			for(JsonNode n: lotteryNumbers) {
				LotteryNumber temp = new LotteryNumber(n);
				// System.out.printf("%s%n", temp.toString());
				listLotteryNumbers.add(temp);
			}
		}
		
		App appInstance = new App();
		if(listMyNumbers.size() != listLotteryNumbers.size()) throw appInstance.new IllegalLengthNotMatchException();
		
		result.put("MyNumbers", listMyNumbers);
		result.put("LotteryNumbers", listLotteryNumbers);
		
		return result;
    }
    
    private static List<RewardResult> checkReward(Map<String, Object> result) {
    	List<MyNumber> myNumbers = (List<MyNumber>) result.get("MyNumbers");
    	List<LotteryNumber> lotteryNumbers = (List<LotteryNumber>) result.get("LotteryNumbers");
    	List<RewardResult> myRewards = new ArrayList<RewardResult>();
    	 
    	for(int i = 0; i < myNumbers.size(); i++) {
    		RewardResult temp = checkEachReward(myNumbers.get(i), lotteryNumbers.get(i));
    		myRewards.add(temp);
    	}
    	
    	for(int i = 0; i < myRewards.size(); i++) {
    		System.out.println(myRewards.get(i));
    	}
    	
    	return myRewards;
    }
    
    private static RewardResult checkEachReward(MyNumber myNumber, LotteryNumber lotteryNumber) {
    	RewardResult myReward = new RewardResult(lotteryNumber.getNo(), myNumber.getNumber());
    	
    	for(int i = 0; i < myNumber.size(); i++) {
    		int ele = myNumber.get(i);
    		if(lotteryNumber.matches(ele)) {
    			myReward.setCountNormal(myReward.getCountNormal() + 1);
    		}
    		if(lotteryNumber.matchesSpecial(ele)) {
    			myReward.setCountSpecial(myReward.getCountSpecial() + 1);
    		}
    	}
    	
    	myReward.transform();
    	
    	return myReward;
    }
    
    private static void marshall(String path, List<RewardResult> rewards) throws JsonGenerationException, JsonMappingException, IOException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    	
    	objectMapper.writeValue(new File(path), rewards);
    }
    
    class IllegalLengthNotMatchException extends RuntimeException {}
}