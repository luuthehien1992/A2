CREATE TABLE "CHAPTER_TABLE" (
	`CHAPTER_ID`	INTEGER,
	`CHAPTER_IMAGE`	TEXT,
	`CHAPTER_QUESTIONS`	TEXT,
	`CHAPTER_IMAGE_QUESTIONS`	TEXT,
	PRIMARY KEY(CHAPTER_ID)
);
INSERT INTO `CHAPTER_TABLE` (CHAPTER_ID,CHAPTER_IMAGE,CHAPTER_IMAGE_QUESTIONS,CHAPTER_QUESTIONS) VALUES (1,'VUA_QUANG_TRUNG','null','[{
  "content": "Vùng đất nổi tiếng với truyền thống võ học",
  "image": null, 
  "answers": ["Phú Yên","Hưng Yên","Bình Định","Nam Định"],
  "answersIdx": 2
  
},
{
  "content": "Tác giả câu thơ: ''Mà may áo vải cờ đào \r\n Giúp dân dựng nước xiết bao công trình''",
  "image": null, 
  "answers": ["Nguyễn Du","Nguyễn Trãi","Bà Huyện Thanh Quang","Ngọc Hân Công Chúa"],
  "answersIdx": 3
  
}
,
{
  "content": "Lê Chiếu Thống cầu viện quân ... ''Cõng rắn cắn gà nhà''",
  "image": null, 
  "answers": ["Hán","Thanh","Minh","Xiêm"],
  "answersIdx": 1
  
}
]
');