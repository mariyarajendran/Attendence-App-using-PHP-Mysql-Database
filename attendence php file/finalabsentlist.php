<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','attendence');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select * from absent ";
 
$res = mysqli_query($con,$sql);
 
$result = array();


			
			
							
							while($row = mysqli_fetch_array($res)){
array_push($result,
array('absents'=>$row['absents'],
'subject'=>$row['subject'],
'department'=>$row['department'],
'section'=>$row['section'],
'year'=>$row['year'],
'dateandtime'=>$row['dateandtime']

));
}
echo json_encode(array("result"=>$result));
		
				 


 




 
mysqli_close($con);
 
?>