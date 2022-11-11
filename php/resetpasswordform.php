<?php
    $connect =mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    $email = $_GET['key'];
    $sql = "SELECT * FROM mahasiswa WHERE email = '$email'";
    $result = mysqli_query($connect, $sql);
    if(mysqli_num_rows($result) === 1){
        if(isset($_POST['submit'])){
            $password = $_POST['password'];
            $cpassword = $_POST['cpassword'];
            if($password == "" && $cpassword == ""){
                echo "Some Field are empty";
            }else{
                if($password == $cpassword){
                    $update = "UPDATE mahasiswa SET password = '$password' WHERE email = '$email'";
                    if(mysqli_query($connect, $update)) {
                        echo "<h2> User Password are changed Successfully !!! Please Login </h2>";
                    } else {
                        echo "Password Changing error reffress and reclick the email link";
                    } 
                } else {
                    echo "Entered Password Not Match";
                }
            }
        } else{
            echo "Click Here To Submit button and Change Password";
        }
    }

?>

<!DOCTYPE html>
 <html>
<head>
<title>Forgot Password</title>
    
</head>
<body>
    <form action="" method="post">
        <h1><?php echo " Welcome ". $email?></h1>
        Masukkan Password Baru : <input type="text" name="password" id="password"> <br>
        Masukkan Konfirmasi Password Bar : <input type="text" name="cpassword" id="cpassword"> <br>
        <input type="submit" name="submit">
    </form>

    
</body>
</html>