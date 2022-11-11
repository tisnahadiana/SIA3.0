<?php
require 'PHPMailer/PHPMailerAutoload.php';
require 'PHPMailer/class.phpmailer.php';
require 'PHPMailer/class.smtp.php';



$connect =mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
$email = $_POST['email'];
$sql = "SELECT * FROM mahasiswa WHERE email = '$email'";

$query = mysqli_query($connect,$sql);
if(mysqli_num_rows($query) === 1){
    $mail = new PHPMailer;
    
    //$mail->SMTPDebug = 3;                               // Enable verbose debug output
    
    $mail->isSMTP();                                      // Set mailer to use SMTP
    $mail->Host = 'smtp.gmail.com';  // Specify main and backup SMTP servers
    $mail->SMTPAuth = true;                               // Enable SMTP authentication

    //sender email
    $mail->Username = 'droomptech@gmail.com';                 // SMTP username
    $mail->Password = '9025droomptech';                           // SMTP password
    $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
    $mail->Port = 587;                                    // TCP port to connect to
    
    $mail->setFrom('droomptech@gmail.com', 'SIAAPS');
    $mail->addAddress($email);     // Add a recipient
    $mail->addReplyTo('droomptech@gmail.com', 'SIAAPS');
    
     // Set email format to HTML
    
    $mail->Subject = 'Forgot Password For SIA APS';
    $mail->Body    = "Klik Link dibawah ini :-
    https://droomp.tech/resetpasswordform.php?key=$email";
 
    
    if(!$mail->send()) {
        echo 'Message could not be sent.';
        echo 'Mailer Error: ' . $mail->ErrorInfo;
    } else {
        $msg["mail"] = "send";
        echo json_encode($msg);
    }
}else{
    echo"Enter A valid email";
}


  
   
?>