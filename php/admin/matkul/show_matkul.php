<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];
    $sql = "SELECT * FROM daftarmatkul WHERE prodi = '$prodi' AND semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $data = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['nama_mk'] = $row['nama_mk'];
        $index['sks'] = $row['sks'];
        $index['prodi'] = $row['prodi'];
        $index['semester'] = $row['semester'];

        array_push($data, $index);
    }
    
    echo json_encode($data);

?>