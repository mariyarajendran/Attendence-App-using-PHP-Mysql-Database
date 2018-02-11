<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','attendence');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 

 
$sql = "select * from seat";
 
$res = mysqli_query($con,$sql);
 
 $response['error']="not load";
 
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('rollno'=>$row[1]
));
}
 $response['error']="Load successfully";
 echo json_encode($response);
 
//echo json_encode(array("result"=>$result));

 
mysqli_close($con);
 
?>