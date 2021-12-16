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

public class App 
{
    public static void main( String[] args )
    {   
    	try {
    		String inputPath = ".\\src\\main\\java\\idv\\ktw\\test\\big_lottery.json";
    		String outputPath = "C:\\Users\\Danny_Wu.PFT\\Desktop\\big_lottery_result.json";
    		
    		Map<String, Object> resultUnmarshall = unmarshall(inputPath);
    		Map<String, Object> resultValidate = validate(resultUnmarshall);
    		List<RewardResult> resultCheckReward = checkReward(resultValidate);
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
    	
		BigLottery bigLottery = objectMapper.readValue(new File(path), BigLottery.class);
		int[][] myNumbers = bigLottery.getMyNumbers();
		List<LotteryNumber> lotteryNumbers = bigLottery.getLotteryNumbers();
		
		List<MyNumber> listMyNumbers = new ArrayList<MyNumber>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		for(int[] number: myNumbers) {
			MyNumber temp = new MyNumber(number);
			listMyNumbers.add(temp);
		}
		
		result.put("MyNumbers", listMyNumbers);
		result.put("LotteryNumbers", lotteryNumbers);
		
		return result;
    }
    
    private static Map<String, Object> validate(Map<String, Object> result) {
    	List<MyNumber> myNumbers = (List<MyNumber>) result.get("MyNumbers");
    	List<LotteryNumber> lotteryNumbers = (List<LotteryNumber>) result.get("LotteryNumbers");
    	
    	myNumbers.forEach((ele) -> ele.checkNumber());
    	lotteryNumbers.forEach((ele) -> {
    		ele.checkNo();
    		ele.checkNumber();
    		ele.checkSpecialNumber();
    	});
    	
    	return result;
    } 
    
    private static List<RewardResult> checkReward(Map<String, Object> result) {
    	List<MyNumber> myNumbers = (List<MyNumber>) result.get("MyNumbers");
    	List<LotteryNumber> lotteryNumbers = (List<LotteryNumber>) result.get("LotteryNumbers");
    	List<RewardResult> myRewards = new ArrayList<RewardResult>();
    	 
    	for(int i = 0; i < myNumbers.size(); i++) {
    		for(int j = 0; j < lotteryNumbers.size(); j++) {
    			RewardResult temp = checkEachReward(myNumbers.get(i), lotteryNumbers.get(j));
        		myRewards.add(temp);
    		}
    	}
    	
    	myRewards.forEach(System.out::println);
    	
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
    
}