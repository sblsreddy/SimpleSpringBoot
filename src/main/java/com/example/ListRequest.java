

import java.util.*;

public class ListRequest {
  //Activity Processor Request {policy=204393, party=112, doctype=Change in Definition of Disability Approval Letter, user=null, caseNumber=null}
  public static void main(String[] args) {
      String existingRequest = "policy=204393, party=112, doctype=Change in Definition of Disability Approval Letter, user=null, caseNumber=null";
      String caseRequest = "policy=[204393,204390,204292], party=[95,100,112,200], doctype=Change in Definition of Disability Approval Letter, user=null, caseNumber=null";
      String policyRequest = "policy=[204393], party=[112,95,200], doctype=Change in Definition of Disability Approval Letter, user=null, caseNumber=[NTN-19-DI-10,NTN-23-DI-11]";
      String partyRequest = "policy=[204393,204390,204292], party=[112], doctype=Change in Definition of Disability Approval Letter, user=null, caseNumber=[NTN-19-DI-10,NTN-23-DI-11]";

      String[] kvPairs = existingRequest.split(",\\s+");
      for(String kvPair : kvPairs){
          System.out.println(kvPair + "\n");
          String[] kv = kvPair.split("=");
          String key = kv[0];
          String value = kv[1];
         // String items[] = value.split(",");
          String replace = value.replace("[","");
          String replace1 = replace.replace("]","");
          List<String> itemsList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
          Map<String, List> requestMap = new HashMap<String, List>();
          Map<String, String> detailsMap = new HashMap<String, String>();
          for(String itemValue : itemsList){
              System.out.println( "Key is : " + key +   " ::: Value is : " + itemValue);
              detailsMap.put(key,itemValue);
          }

      }
  }

}
