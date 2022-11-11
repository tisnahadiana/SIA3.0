<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];
    $sql = "SELECT * FROM khs WHERE prodi = '$prodi' AND semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $data = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['nim'] = $row['nim'];
        $index['nama'] = $row['nama'];
        $index['prodi'] = $row['prodi'];
        $index['semester'] = $row['semester'];

        array_push($data, $index);
    }
    
    echo json_encode($data);

?>