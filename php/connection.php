<?php
    $connect =mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    if ($connect){
        echo"Connection Estabilished";
    } else {
        echo "Connection Error";
    }
?>