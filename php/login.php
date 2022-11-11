<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];
    $password = $_POST["password"];

    $sql = "SELECT * FROM mahasiswa WHERE nim = '$nim' AND password = '$password'";
    $result = array();
    $result['data'] = array();
    $responce = mysqli_query($connect,$sql);

    if(mysqli_num_rows($responce) === 1){
        $row = mysqli_fetch_assoc($responce);
        $ds['nim'] = $row['nim'];
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