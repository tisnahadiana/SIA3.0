<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $id_admin = $_POST["id_admin"];
    $password = $_POST["password"];

    $sql = "SELECT * FROM admin WHERE id_admin = '$id_admin' AND password = '$password'";
    $result = array();
    $result['data'] = array();
    $responce = mysqli_query($connect,$sql);

    if(mysqli_num_rows($responce) === 1){
        $row = mysqli_fetch_assoc($responce);
        $ds['id_admin'] = $row['id_admin'];
        $ds['nama'] = $row['nama'];

        array_push($result['data'], $ds);
        $result['status'] = 'success';
        echo json_encode($result);
        mysqli_close($connect);
    }else{
        $result['status'] = 'success';
        echo json_encode($result);
        mysqli_close($connect);
    }


?>