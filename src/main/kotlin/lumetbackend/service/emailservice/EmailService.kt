package lumetbackend.service.emailservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
            private val javaMailSender : JavaMailSender,
            @Value("\${spring.mail.username}") private val senderEmail: String,
){

    fun sendEmailToken(receiver: String, emailtoken: String) {
        val message = SimpleMailMessage()
        message.setFrom(senderEmail)
        message.setTo(receiver)
        message.setSubject("Mail confirmation")
        message.setText(emailtoken)
        javaMailSender.send(message)
    }
}