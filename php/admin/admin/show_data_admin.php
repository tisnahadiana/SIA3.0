<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $sql = "SELECT * FROM admin";
    
    $result = mysqli_query($connect,$sql);
    $data = array();

    while($row = mysqli_fetch_assoc($result)){
        $index['ID'] = $row['id_admin'];
        $index['nama'] = $row['nama'];
        $index['email'] = $row['email'];

        array_push($data, $index);
    }
    
    echo json_encode($data);

?>