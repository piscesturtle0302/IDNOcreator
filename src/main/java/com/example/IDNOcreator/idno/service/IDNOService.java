package com.example.IDNOcreator.idno.service;

//import com.example.IDNOcreator.common.database.dao.IdnoCreatorDao;
//import com.example.IDNOcreator.common.database.entity.IdnoCreator;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IDNOService {

    //@Autowired
    //IdnoCreatorDao idnoCreatorDao;

    private final char[] pidCharArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    // 2020新式證號判斷修改新增'8','9'，下方使用二元搜尋法(排序後搜尋)，需依照ASCII碼的編號排序建立陣列
    private final char[] pidCharArraySecond = { '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    // 證號英文轉碼對應
    private int[] pidIDtoInt = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26,
            27, 28, 29, 32, 30, 31, 33 };

    // 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod 10]
    private final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3,
            12, 10 };

    // 原居留證第二碼英文字應轉換為10~33，並僅取個位數*8，這裡直接取[(個位數*8) mod 10]
    private final int[] pidResidentSecondInt = { 4, 2, 0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6,
            0, 8, 4};

    /** 隨機建立一組新式居留證號 */
    public String createNewResidenceIdNo(){
        Random random = new Random();

        int firstInt = random.nextInt(26);
        char firstChar  = (char) ((char) firstInt + 65);

        int secondInt = random.nextInt(2) + 8;
        char secondChar = (char) ((char) secondInt + 48);

        int bodyInt;
        String bodyString = "";
        for(int i=1 ; i<=7 ; i++){
            bodyInt = random.nextInt(10);
            bodyString += String.valueOf(bodyInt);
        }

        String newResidentString = String.valueOf(firstChar) + String.valueOf(secondChar) + bodyString;

        newResidentString += newCheckCode(newResidentString);

        //IdnoCreator idnoCreator = new IdnoCreator();
        //idnoCreator.setResIdno(newResidentString);
        //idnoCreatorDao.save(idnoCreator);

        return newResidentString;
    }

    /** 確認新式居留證號格式 */
    public boolean  checkNewResidenceIdNo(String str){
        if(str.matches("[A-Z]{1}[8-9]{1}[0-9]{8}")) {
            final char[] strArr = str.toCharArray();
            return strArr[9] == newCheckCode(str);
        }else{
            return false;
        }
    }

    /** 確認新式居留檢查碼 */
    public char newCheckCode(String str){

        final char[] strArr = str.toCharArray();

        int verifyNum = 0;
        verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
        verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArraySecond, strArr[1])];
        for (int i = 2, j = 7; i < 9; i++, j--) {
            verifyNum += Character.digit(strArr[i], 10) * j;
        }
        verifyNum = (10 - (verifyNum % 10)) % 10;

        return (char) ((char) verifyNum + 48);
    }

    /** 顯示新式居留證號邊碼拆解 */
    public Map<String,Object> detailNewResidenceIdNo (String str){
        Map<String,Object> resultMap = new HashMap<>();

        List<Integer> idToInt = new ArrayList<>();//11碼
        List<Integer> intToCount = new ArrayList<>();//10碼
        List<Integer> countDivideTen = new ArrayList<>();//10碼
        List<Integer> checkCode = new ArrayList<>();//1碼

        int verifyCode = 0;

        char[] strArr = str.toCharArray();
        int firstAndSecondInt = pidIDtoInt[Arrays.binarySearch(pidCharArray,strArr[0])];
        char[] firstAndSecondChar = String.valueOf(firstAndSecondInt).toCharArray();

        for(int i=0 ; i<strArr.length+1 ; i++){
            if(i<=1){
                idToInt.add(Integer.parseInt(String.valueOf(firstAndSecondChar[i])));
            }else{
                idToInt.add(Integer.parseInt(String.valueOf(strArr[i-1])));
            }
        }

        for(int j=0 ; j<idToInt.size()-1 ; j++){
            if(j==0){
                intToCount.add(idToInt.get(j));
            }else{
                intToCount.add(idToInt.get(j)*(10-j));
            }
        }

        for (Integer integer : intToCount) {
            countDivideTen.add(integer % 10);
            verifyCode += integer % 10;
        }

        checkCode.add((10 - (verifyCode % 10)) % 10);

        resultMap.put("idToInt",idToInt);
        resultMap.put("intToCount",intToCount);
        resultMap.put("countDivideTen",countDivideTen);
        resultMap.put("checkCode",checkCode);

        return resultMap;
    }
}
