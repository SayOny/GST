# Log Format 로그의 형식



- 로그의 구조는 32KB 크기의 블럭의 시퀀스로 되어있다.     
- 한 블럭 안에는 `Checksum(4bytes), Length(2bytes), Type(1byte), Data` 부분으로 나뉘어져 있다.      



<img src="https://drive.google.com/u/1/uc?id=1E_j12nGBrGLoZ5Ze--pg9UoPFvqgJ26s&export=download">    


- `checksum`은 데이터의 손상을 확인할 때 사용한다.   
- `length`는 데이터의 길이를 말한다.   
- 쓰고자 하는 데이터의 크기가 한 블럭에 담긴다면 `type` 부분에 `Full(1)`이 표기되겠지만,       
그러지 못하고 데이터가 여러 블럭에 담겨야 할 때 그 데이터가 기록되는 가장 앞 부분의 블럭의 `type`에는 `First(2)`가 표기되고       
가장 마지막 블록은 `Last(4)`가 표기된다.        
그리고 그 중간에 담기는 모든 블럭들은 `Middle(3)`로 표기된다.      
- `data` 부분에는 데이터가 쓰인다.   


# log::reader 파일  


  
`log::reader` 파일에는   
`Reader` 클래스와 몇 가지 함수가 있다.    


 
<img src="https://drive.google.com/u/1/uc?id=1n0iBamRTZTfV4Nj-i2GqJ0paLpNYuQ8L&export=download">     


  
나는 이 `bool Reader::ReadRecord(Slice* record, std::string* scratch)` 함수를 중심으로 이 파일의 흐름을 살펴보고자 했다.    



![a-3]( https://drive.google.com/u/1/uc?id=14NWw8RAqeUYsQvzb2AxUMACrfSjdEzSN&export=download)      



- `ReadRecord()` 함수가 실행되면 먼저 이니셜 블록의 위치를 찾는데   
SkipToInitialBlock()함수는 이니셜 블록의 위치가 있는 곳까지 오프셋을 옮긴다.   
- 이후 `while`문을 돌면서 읽고자 하는 데이터를 읽게 된다.    
- while문을 도는 동안에는 `record_type`이라는 정수형 변수에 `ReadPhysicalRecord()`함수의 리턴 값을 받는다.     
- 이 함수는 위에서 언급했듯이 record의 `type`을 읽고 그를 반환하거나 에러가 있으면 그 에러를 알려준다. 그래서 `type`의 종류가 조금 늘었는데,     
`kEof, kBadRecord`가 그것이다. 


   
 <img src="https://drive.google.com/u/1/uc?id=1OH37ofybb-_cghK5a_gu4Ten8XQTOQpt&export=download">    


   
- 읽으려는 데이터가 몇 개의 블럭에 걸쳐 있는지 알기 위해서 혹은 에러가 있는지 확인하기 위해     
위에 `ReadPhysicalRecord()` 함수를 호출하고 받은 `record_type`값을 이용한다.    


  
- `switch`문에서는 각 블럭의 `type`을 읽고 블럭을 더 읽을 것인지 판단하며    
에러가 있으면 이 또한 처리하는 과정이 있다.  



- 그리고 `ReadRecord()` 함수의 매개변수로는 `record`와 `scratch`를 받는데,     
읽고자 하는 데이터가 여러 블록으로 나뉘어져 있을 경우 `scratch`에 각 블럭의 데이터를 `append`해주었다가     
마지막 블럭을 만나면 한번에 `record`에 준다.    



- 또한 `in_fragmented_record`라는 변수가 있는데,    
이 변수는 읽고자 하는 블럭이 여러 개로 나뉘어져 있는지 판단할 때 사용하며 기본값은 false이다.   



1. `kFullType`의 경우 데이터를 읽어 바로 `record`에 준다음 `true`를 반환하며 그대로 함수를 마친다.       



2. `kFirstType`의 경우 `scratch`에 데이터를 `append`한 후        
`in_fragmented_record` 변수를 `true`로 설정하여 뒤에 블럭이 더 있음을 알리는 역할을 하게끔 만든다.      
여기서는 `in_fragmented_record`가 false여야 정상이므로 그렇지 않다면 Report해준다.   



3. `kMiddleType`의 경우 `scratch`에 데이터를 `append` 해준다.     
여기서는 `in_fragmented_record`가 false라면 핸들링 해준다.     



4. `kLastType`의 경우 `scratch`에 데이터를 `append` 해준 후       
여태 추가했던 데이터가 담겨있는 `scratch`를 `Slice` 객체로 바꿔서 `record`에 준다.   
마찬가지로 `in_fragmented_record`가 `false`라면 핸들링 해준다.     



5. `kEof`의 경우는 더이상 읽을 블럭이 없을 때이다.    
그러므로 false를 반환하여 그대로 함수를 마친다.    



 6. `kBadRecord`의 경우 `checksum`이 맞지 않거나 레코드의 길이가 0이거나 등     
 오류가 있어 물리 레코드에서 읽어오지 못한 경우에 처리된다.   



 7. `모든 keyType에 해당되지 않는 경우`는 오류를 알린다.   







지금까지 Log format를 살펴보고 log::reader파일을      
`bool Reader::ReadRecord(Slice* record, std::string* scratch)` 함수를 중심으로 보았다.       






