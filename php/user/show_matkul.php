<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $semester = $_POST["semester"];
    $sql = "SELECT * FROM daftarmatkul WHERE semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $matkul = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['nama_mk'] = $row['nama_mk'];
        $index['sks'] = $row['sks'];

        array_push($matkul, $index);
    }
    
    echo json_encode($matkul);

?>