<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $semester = $_POST["semester"];
    $sql = "SELECT * FROM daftarmatkul WHERE semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $matkul = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['matkul1'] = $row['nama_mk'];
        $index['sks1'] = $row['sks'];
        $index['matkul2'] = $row['nama_mk'];
        $index['sks2'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['matkul1'] = $row['nama_mk'];
        // $index['matk'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];
        // $index['sks'] = $row['sks'];

        array_push($matkul, $index);
    }
    
    echo json_encode($matkul);

?>