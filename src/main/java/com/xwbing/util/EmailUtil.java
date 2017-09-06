package com.xwbing.util;

import com.xwbing.entity.model.EmailModel;
import com.xwbing.exception.BusinessException;
import org.apache.commons.lang.StringUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * 说明:邮箱工具类
 * 创建日期: 2016年12月7日 下午12:30:15
 * 作者: xwb
 */
public class EmailUtil {
    /**
     * 发送邮件
     *
     * @param emailModel
     * @return
     */
    public static boolean sendTextEmail(EmailModel emailModel) {
        try {
            boolean result = true;
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();// 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
            String serverHost = emailModel.getServerHost();// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
            if (StringUtils.isEmpty(serverHost)) {
                result = false;
                throw new BusinessException("发送邮件主机不能为空");
            }
            props.setProperty("mail.transport.protocol",
                    emailModel.getProtocol());// 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", serverHost); // 发件人的邮箱的
            // SMTP服务器地址
            if (emailModel.getServerPort() != null) {
                props.setProperty("mail.smtp.port", emailModel.getServerPort() + "");// 发送邮件服务器端口
            }
            props.setProperty("mail.smtp.auth", emailModel.isAuth() + "");// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getDefaultInstance(props);// 用刚刚设置好的props对象构建一个session
            session.setDebug(true);// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
            // 3. 创建一封邮件,真正要发送时, 邮箱必须是真实有效的邮箱。
            MimeMessage message = new MimeMessage(session);// 用session为参数 创建邮件对象
            // From: 发件人
            String fromEmail = emailModel.getFromEmail();
            if (StringUtils.isEmpty(fromEmail)) {
                result = false;
                throw new BusinessException("发送邮箱不能为空");
            }
            message.setFrom(new InternetAddress(emailModel.getFromEmail()));
            // To: 收件人
            if (StringUtils.isEmpty(emailModel.getToEmail())) {
                result = false;
                throw new BusinessException("接收邮箱不能为空");
            }
            InternetAddress[] toEmailList = InternetAddress.parse(emailModel
                    .getToEmail());// toEmail格式为"aaa,bbb,..."或"aaa"
            message.setRecipients(Message.RecipientType.TO, toEmailList);
            /*
             * message.addRecipient(MimeMessage.RecipientType.TO,new
			 * InternetAddress("xx@xx.com")); // To: 增加收件人（单个）（可选）
			 * message.setRecipient(MimeMessage.RecipientType.CC,new
			 * InternetAddress("xx@xx.com")); // Cc: 抄送（可选）
			 * message.setRecipient(MimeMessage.RecipientType.BCC,new
			 * InternetAddress("xx@xx.com"));// Bcc: 密送（可选）
			 */
            // Subject: 邮件主题
            if (StringUtils.isEmpty(emailModel.getSubject())) {
                result = false;
                throw new BusinessException("邮件主题不能为空");
            }
            message.setSubject(emailModel.getSubject());
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(emailModel.getCentent(), "text/html; charset=utf-8");
            multipart.addBodyPart(html);
            message.setContent(multipart);
            message.setSentDate(emailModel.getSendTime());// 设置显示的发件时间
            message.saveChanges();// 保存邮件
            // 4.根据 Session 获取邮件传输对象
            Transport transport = session.getTransport("smtp");
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器
            if (StringUtils.isEmpty(emailModel.getPassword())) {
                result = false;
                throw new BusinessException("发送邮箱密码不能为空");
            }
            transport.connect(serverHost, fromEmail, emailModel.getPassword());// 这里认证的邮箱必须与
            // message中的发件人邮箱一致，否则报错
            // 6.发送邮件, 发到所有的收件地址, message.getAllRecipients()
            // 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
            return result;
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将toemail数组转为"aaa,bbb"格式字符串
     *
     * @param toEmailArray
     * @return
     */
    public static String getMailList(String[] toEmailArray) {
        StringBuffer toList = new StringBuffer();
        if (toEmailArray != null && toEmailArray.length > 0) {
            for (int i = 0; i < toEmailArray.length; i++) {
                if (toList.length() > 0)
                    toList.append(",");
                toList.append(toEmailArray[i]);
            }
            return toList.toString();
        }
        return "";
    }

    public static void main(String[] args) {
        EmailModel emailModel = new EmailModel();
        emailModel.setServerHost("smtp.163.com");
        emailModel.setServerPort(25);
        emailModel.setProtocol("smtp");
        emailModel.setAuth(true);
//		emailModel.setSendTime();默认当前时间
        emailModel.setFromEmail("xwbing2009@163.com");
        emailModel.setToEmail("786461501@qq.com,xiangwb@drore.com");
        emailModel.setAttachFileNames(null);
        emailModel.setPassword("xwbing900417");
        emailModel.setSubject("测试邮件");
        emailModel.setCentent("邮件功能测试,请勿回复");
        boolean b = sendTextEmail(emailModel);
        System.out.println(b);
    }
}
