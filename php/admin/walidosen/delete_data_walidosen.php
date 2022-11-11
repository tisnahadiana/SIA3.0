<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nidn = $_POST["nidn"];

    $sql = "DELETE FROM walidosen WHERE nidn = '$nidn'";

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