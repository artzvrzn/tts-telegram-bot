# tts-telegram-bot
Text-To-Speech telegram bot implementation. 

User sends texts to the telegram-bot.
Dispatcher receives and pushes update to the rabbitmq queue.
TTS-service listens to the queue, pulls update out there
and sends text from this update to aws-polly to process.
After audio is received, TTS-service sends it to the s3-storage
and pushes url to rabbitmq queue which is listened by dispatcher.
Dispatcher responds to user, sending him this file.
