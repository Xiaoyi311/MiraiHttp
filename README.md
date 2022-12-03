# MiraiHttp —— 全新的 Mirai-Http-Api Java 工具

## 关于项目

MiraiHttp 由 Xiaoyi311 制作，用于在 Java 快速连接 Mirai-Http-Api 并监听事件与发送信息，帮助开发者快速入门

## 项目依赖

com.alibaba:fastjson —— 2.0.20

## 使用教程

在项目中，全部的方法与类全都具有注释，简单易懂，以下为简单的机器人使用示例

    public class Main {
       public void main(String[] args) {
          MiraiHttpSession session;

          /* 不太推荐使用此方法，如果太久未绑定，Session 会失效
          try {
              session = MiraiHttp.createSession("xxx", "127.0.0.1:8080");
              session.bindRobot(1908351852L);
          } catch (VerifyKeyError e) {
              System.out.print("验证密钥错误");
              return;
          } catch (SessionOutDate e) {
              System.out.print("Session 已失效，需重新获取");
              return;
          } catch (RobotNotFound e) {
              System.out.print("指定机器人未找到");
              return;
          }
          */

          //此方法需要快速注册监听器，否则部分信息可能无法收到
          try {
              session = MiraiHttp.createSession("xxx", "127.0.0.1:8080", 1908351852L);
          } catch (VerifyKeyError e) {
              System.out.print("验证密钥错误");
              return;
          } catch (RobotNotFound e) {
              System.out.print("指定机器人未找到");
              return;
          }

          MiraiHttp.registerListener(new MiraiListener(), session);
       }

       public static class MiraiListener implements MiraiEventListener {
          @Override
          public void onGroupMessage(GroupMessageEvent event) {
              String message = MessageChain.toMiraiString(event.messages);

              //如果消息以 ".hello" 开头
              if (message.startsWith(".hello")){
                  event.session.getApi().sendGroupMessage(
                          event.sender.group.id,
                          new MessageChain[]{
                                  new Plain("你好哇!")
                          }
                  );
              }
          }
       }
    }

## 项目文档

见 GitPage 或者 docs 分支
