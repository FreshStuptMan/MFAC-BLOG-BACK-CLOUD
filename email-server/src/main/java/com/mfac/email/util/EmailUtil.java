package com.mfac.email.util;

import com.mfac.email.constant.EmailConstant;
import com.mfac.email.pojo.entity.FriendLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class EmailUtil {
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送友链上架通知邮件
     * @param friendLink
     */
    public void FriendLinkUpEmailSender(FriendLink friendLink) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String friendLinkInfo = FriendLinkInfo(friendLink, EmailConstant.FRIEND_LINK_UP_NOTIFY_EMAIL);
        // 拼接和设置正文
        String text = friendLink.getAuthor()+ EmailConstant.FRIEND_LINK_UP_EMAIL_TEXT +friendLinkInfo;
        // 发送邮件
        EmailSender(friendLink.getEmail(), text, EmailConstant.FRIEND_LINK_UP_EMAIL_TITLE);
    }

    /**
     * 发送友链下架通知邮件
     * @param friendLink
     */
    public void FriendLinkDownEmailSender(FriendLink friendLink) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String friendLinkInfo = FriendLinkInfo(friendLink, EmailConstant.FRIEND_LINK_DOWN_NOTIFY_EMAIL);
        // 拼接和设置正文
        String text = friendLink.getAuthor()+ EmailConstant.FRIEND_LINK_DOWN_EMAIL_TEXT +friendLinkInfo;
        // 发送邮件
        EmailSender(friendLink.getEmail(), text, EmailConstant.FRIEND_LINK_DOWN_EMAIL_TITLE);
    }


    /**
     * 通用邮件发送
     * @param aimEmail
     * @param text
     * @param title
     */
    private void EmailSender (String aimEmail, String text, String title) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置正文
        mailMessage.setText(text);
        // 设置发送者
        mailMessage.setFrom(EmailConstant.EMAIL_SENDER);
        // 设置接收者
        mailMessage.setTo(aimEmail);
        // 设置邮件标题
        mailMessage.setSubject(title);
        // 发送邮件
        javaMailSender.send(mailMessage);
    }

    /**
     * 拼接友链信息
     * @param friendLink
     * @return
     */
    public String FriendLinkInfo(FriendLink friendLink, Integer emailType) {
        String friendLinkInfo = "";
        if (EmailConstant.FRIEND_LINK_UP_NOTIFY_EMAIL.equals(emailType)) {
            friendLinkInfo = "{\n" +
                    "id=" + friendLink.getId() + "\n" +
                    "name=" + friendLink.getName() + "\n" +
                    "link=" + friendLink.getLink() + "\n" +
                    "description=" + friendLink.getDescription() + "\n" +
                    "author=" + friendLink.getAuthor() + "\n" +
                    "email=" + friendLink.getEmail() + "\n" +
                    "}";
        } else if (EmailConstant.FRIEND_LINK_DOWN_NOTIFY_EMAIL.equals(emailType)) {
            friendLinkInfo = "{\n" +
                    "id=" + friendLink.getId() + "\n" +
                    "name=" + friendLink.getName() + "\n" +
                    "link=" + friendLink.getLink() + "\n" +
                    "description=" + friendLink.getDescription() + "\n" +
                    "author=" + friendLink.getAuthor() + "\n" +
                    "email=" + friendLink.getEmail() + "\n" +
                    "downReason=" + friendLink.getDownReason() + "\n" +
                    "}";
        }
        return friendLinkInfo;
    }
}
