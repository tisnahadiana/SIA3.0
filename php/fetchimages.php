<?php 
     $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

     $nim = $_POST["nim"];

    if($_SERVER['REQUEST_METHOD'] == 'POST')
    {

        $result = array();
        $result['data'] = array();
        $select = "SELECT *from khs WHERE nim = '$nim'";
        $responce = mysqli_query($connect,$select);

        while($row = mysqli_fetch_array($responce))
        {

            $index['nim'] = $row['1'];
            $index['image'] = $row['3'];

            array_push($result['data'], $index);

        }

        $result["success"]="1";
        echo json_encode($result);
        mysqli_close($connect);


    }


?>