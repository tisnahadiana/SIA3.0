<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $nim = $_POST['nim'];
    $photo = $_POST['photo'];

    $path = "profile_user/$id.jpeg";
    $finalPath = "https://droomp.tech/".$path;
  
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $sql = "UPDATE mahasiswa SET photo='$finalPath' WHERE nim = '$nim'";

    if(mysqli_query($connect, $sql)){

        if(file_put_contents($path, base64_decode($photo))){

            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($connect);

        }
  
    }
}


?>