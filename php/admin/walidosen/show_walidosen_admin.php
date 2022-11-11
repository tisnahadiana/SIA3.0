<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];
    $sql = "SELECT * FROM walidosen WHERE prodi = '$prodi' AND semester = '$semester'";
    
    $result = mysqli_query($connect,$sql);
    $data = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['nidn'] = $row['nidn'];
        $index['nama'] = $row['nama'];
        $index['email'] = $row['email'];
        $index['prodi'] = $row['prodi'];
        $index['semester'] = $row['semester'];

        array_push($data, $index);
    }
    
    echo json_encode($data);

?>