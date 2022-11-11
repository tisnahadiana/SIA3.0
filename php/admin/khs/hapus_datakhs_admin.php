<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];

    $sql = "DELETE FROM khs WHERE nim = '$nim'";

    $result = mysqli_query($con,$sql);


    if($result){
        $result['status'] = 'success';
        echo json_encode($result);
        mysqli_close($con);
    }
    else{
        $result['status'] = 'error';
        echo json_encode($result);
        mysqli_close($con);
    }


?>