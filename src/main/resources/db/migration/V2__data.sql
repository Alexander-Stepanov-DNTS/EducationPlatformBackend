--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-05 19:08:24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4939 (class 0 OID 17420)
-- Dependencies: 243
-- Data for Name: achievement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.achievement (id, title, description) FROM stdin;
2	Первый курс	Завершил первый курс
3	Активный ученик	Завершил пять курсов
4	Мастер уроков	Завершил десять уроков
5	Викторина мастер	Завершил первую викторину
1	Achievement Name	Achievement Description
\.


--
-- TOC entry 4916 (class 0 OID 17209)
-- Dependencies: 220
-- Data for Name: direction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.direction (id, name, description) FROM stdin;
1	Информационные технологии	Изучите современные и востребованные технологии в мире IT. От программирования до кибербезопасности, наши курсы помогут вам стать настоящим профессионалом.
2	Бизнес и менеджмент	Освойте ключевые аспекты управления бизнесом и проектоами. Наши курсы помогут вам развить лидерские качества и стратегическое мышление.
3	Подготовка к ЕГЭ	Эффективная подготовка к ЕГЭ по различным предметам. Получите высокие баллы и обеспечьте себе поступление в лучшие вузы страны.
4	Творчество и дизайн	Развивайте свои творческие способности и навыки дизайна. Узнайте секреты профессионалов и воплотите свои идеи в жизнь.
5	Иностранные языки	Овладейте новыми языками с помощью наших курсов. Улучшите свои навыки общения и откройте для себя новые культуры и возможности.
6	Личностный рост	Стремитесь к самосовершенствованию и личностному развитию. Наши курсы помогут вам раскрыть свой потенциал и достичь поставленных целей.
\.


--
-- TOC entry 4918 (class 0 OID 17217)
-- Dependencies: 222
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, name, description, direction_id) FROM stdin;
1	Языки программирования	Курсы по программированию на различных языках	1
2	Веб-разработка	Курсы по веб-разработке и созданию сайтов	1
3	Маркетинг	Курсы по маркетингу и продвижению	2
4	Математика	Курсы по математике и подготовка к экзаменам	3
5	Письмо	Курсы по креативному письму и написанию текстов	4
\.


--
-- TOC entry 4920 (class 0 OID 17230)
-- Dependencies: 224
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course (id, name, description, price, is_progress_limited, category_id, picture_url) FROM stdin;
1	Многопоточный Python	С этим курсом вы освоите многопоточное программирование и откроете для себя мир высокопроизводительных приложений!	1000	f	1	python.png
2	Веб-разработка с использованием JavaScript	Научитесь создавать интерактивные веб-приложения с использованием JavaScript.	1200	f	2	js.png\n
3	Основы маркетинга	Познакомьтесь с основами маркетинга, изучите стратегии продвижения продуктов и услуг.	800	t	3	Marketing.png
4	Подготовка к ЕГЭ по математике	Подготовьтесь к ЕГЭ по математике с помощью этого курса, включающего теоретические материалы и практические задания.	1500	t	4	Math.png\n
5	Креативное письмо	Научитесь писать креативные тексты, статьи и эссе с помощью этого увлекательного курса.	600	f	5	Letter.png
\.


--
-- TOC entry 4937 (class 0 OID 17379)
-- Dependencies: 241
-- Data for Name: course_material; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course_material (id, course_id, material_title, material_url) FROM stdin;
1	1	Многопоточный питон - статья	https://habr.com/ru/articles/764420/
2	1	Zen of Python: история, реализация и пасхалки	https://habr.com/ru/articles/825884/
3	2	Веб-разработка. С чего начать	https://htmlacademy.ru/blog/job/programming-start
4	2	Раздел веб-разработки на METANIT	https://metanit.com/web/
\.


--
-- TOC entry 4914 (class 0 OID 17201)
-- Dependencies: 218
-- Data for Name: institution; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.institution (id, name, type) FROM stdin;
1	IT Academy	Университет
2	Business School	Школа
3	Math Institute	Институт
4	Creative Center	Центр
5	Science School	Школа
\.


--
-- TOC entry 4912 (class 0 OID 17195)
-- Dependencies: 216
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, name) FROM stdin;
1	Администратор
2	Инструктор
3	Студент
\.


--
-- TOC entry 4922 (class 0 OID 17243)
-- Dependencies: 226
-- Data for Name: site_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.site_user (id, email_address, password, signup_date, role_id, institution_id, login) FROM stdin;
1	ivan.ivanov@example.com	hashedpassword1	2023-01-01	3	1	Иван Иванов
2	maria.petrova@example.com	hashedpassword2	2023-01-02	2	2	Машка Петрова
3	alex.sidorov@example.com	hashedpassword3	2023-01-03	3	3	Алексей Сидоров
4	elena.ivanova@example.com	hashedpassword4	2023-01-04	2	4	Лена Иванова
5	petr.petrov@example.com	hashedpassword5	2023-01-05	3	5	Петр Петров
7	Testing@gmail.com	Testing	2024-07-30	3	1	Testing
\.


--
-- TOC entry 4931 (class 0 OID 17310)
-- Dependencies: 235
-- Data for Name: enrolment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enrolment (course_id, student_id, enrolment_datetime, completed_datetime, is_author) FROM stdin;
1	1	2023-01-01 10:00:00	2023-01-10 10:00:00	f
2	2	2023-01-02 11:00:00	\N	t
3	3	2023-01-03 12:00:00	2023-01-13 12:00:00	f
4	4	2023-01-04 13:00:00	\N	t
5	5	2023-01-05 14:00:00	2023-01-15 14:00:00	f
2	1	2024-07-25 00:00:00	\N	f
\.


--
-- TOC entry 4924 (class 0 OID 17263)
-- Dependencies: 228
-- Data for Name: lesson; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lesson (id, name, number, video_url, lesson_details, course_order, course_id) FROM stdin;
1	Введение в Python	1	https://www.youtube.com/watch?v=fp5-XQFr_nk	Основные концепции и установка среды разработки.	1	1
2	Переменные и типы данных	2	https://www.youtube.com/watch?v=Fn7gwzrI0Oo	Изучение переменных и типов данных в Python.	2	1
3	Условные операторы	3	https://www.youtube.com/watch?v=P8XvMo_NNvo	Изучение условных операторов if-else.	3	1
4	Введение в JavaScript	1	https://www.youtube.com/watch?v=aiBZl4VgTiQ	Основные концепции и установка среды разработки.	1	2
5	Работа с DOM	2	https://www.youtube.com/watch?v=yp5Cj36g5Uw	Изучение работы с объектной моделью документа (DOM).	2	2
\.


--
-- TOC entry 4926 (class 0 OID 17276)
-- Dependencies: 230
-- Data for Name: quiz; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quiz (id, title, description, created_date, is_active, due_date, reminder_date, course_order, course_id) FROM stdin;
1	Тест по основам HTML	Тест на знание основ HTML.	2023-01-10 00:00:00	t	2023-01-15 00:00:00	2023-01-12 00:00:00	3	1
2	Тест по основам CSS	Тест на знание основ CSS.	2023-01-20 00:00:00	t	2023-01-25 00:00:00	2023-01-22 00:00:00	5	2
3	Тест по переменным в Python	Проверка знаний о переменных и типах данных в Python.	2023-02-01 00:00:00	t	2023-02-05 00:00:00	2023-02-03 00:00:00	2	1
4	Тест по условным операторам	Проверка знаний об условных операторах в Python.	2023-02-10 00:00:00	t	2023-02-15 00:00:00	2023-02-12 00:00:00	4	1
5	Тест по JavaScript	Проверка знаний о JavaScript и DOM.	2023-03-01 00:00:00	t	2023-03-05 00:00:00	2023-03-03 00:00:00	3	2
\.


--
-- TOC entry 4928 (class 0 OID 17289)
-- Dependencies: 232
-- Data for Name: quiz_question; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quiz_question (id, quiz_id, question_title, many_answers) FROM stdin;
1	1	Что такое HTML?	f
2	1	Какой тег используется для создания гиперссылки?	f
3	2	Что такое CSS?	f
4	2	Какое CSS-свойство используется для изменения цвета текста?	f
5	3	Что такое переменная в Python?	f
6	1	Какой тег используется для вставки изображения?	f
7	1	Какой атрибут используется для указания URL-адреса гиперссылки?	f
8	1	Какой тег используется для создания списка с маркерами?	f
9	3	Какой тип данных в Python используется для хранения целых чисел?	f
10	3	Какое ключевое слово используется для создания переменной в Python?	f
11	3	Какой тип данных в Python используется для хранения текста?	f
12	4	Какой синтаксис используется для условного оператора в Python?	f
13	4	Какой ключевое слово используется для добавления альтернативных условий в Python?	f
14	4	Какой оператор используется для сравнения значений на равенство в Python?	f
\.


--
-- TOC entry 4930 (class 0 OID 17300)
-- Dependencies: 234
-- Data for Name: quiz_answer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quiz_answer (id, question_id, answer_text, is_correct) FROM stdin;
1	1	Язык программирования	f
2	1	Язык разметки	t
3	1	Стиль веб-страницы	f
4	1	Скриптовый язык	f
5	2	<link>	f
6	2	<a>	t
7	2	<href>	f
8	2	<url>	f
9	3	Язык программирования	f
10	3	Язык разметки	f
11	3	Стиль веб-страницы	t
12	3	Скриптовый язык	f
13	4	font-color	f
14	4	text-color	f
15	4	color	t
16	4	font-style	f
17	5	Объект, хранящий данные	t
30	6	<img>	t
31	6	<image>	f
32	6	<pic>	f
33	6	<src>	f
34	7	href	t
35	7	src	f
36	7	link	f
37	7	url	f
38	8	<ul>	t
39	8	<ol>	f
40	8	<li>	f
41	8	<list>	f
42	9	Integer	t
43	9	Float	f
44	9	String	f
45	9	Boolean	f
46	10	var	f
47	10	let	f
48	10	const	f
49	10	Переменные создаются без ключевого слова	t
50	11	String	t
51	11	Text	f
52	11	Char	f
53	11	List	f
54	12	if	t
55	12	if-else	f
56	12	when	f
57	12	switch	f
58	13	elif	t
59	13	else-if	f
60	13	elseif	f
61	13	elif-else	f
62	14	==	t
63	14	=	f
64	14	===	f
65	14	is	f
\.


--
-- TOC entry 4935 (class 0 OID 17356)
-- Dependencies: 239
-- Data for Name: review; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.review (id, user_id, course_id, created_date, score, review_text) FROM stdin;
1	1	1	2023-01-10 10:30:00	5	Отличный курс! Узнал много нового.
2	2	2	2023-01-20 11:30:00	4	Хорошо объяснены основные концепции.
3	3	3	2023-02-01 12:30:00	5	Очень полезный курс для начинающих.
4	4	4	2023-02-10 13:30:00	4	Интересные задания и хороший материал.
5	5	5	2023-03-01 14:30:00	5	Курс помог мне улучшить навыки.
6	1	1	2024-07-04 15:13:26.477109	5	Очень крутой курс!
7	1	1	2024-07-04 15:24:53.330479	5	Мне очень понравился данный курс
8	1	1	2024-07-16 12:19:50.293332	5	123
\.


--
-- TOC entry 4932 (class 0 OID 17325)
-- Dependencies: 236
-- Data for Name: student_lesson; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student_lesson (student_id, lesson_id, completed_datetime) FROM stdin;
1	1	2023-01-01 10:30:00
1	2	2023-01-02 10:30:00
1	3	2023-01-03 10:30:00
2	4	2023-01-02 11:30:00
2	5	2023-01-03 11:30:00
\.


--
-- TOC entry 4933 (class 0 OID 17340)
-- Dependencies: 237
-- Data for Name: student_quiz_attempt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student_quiz_attempt (student_id, quiz_id, attempt_datetime, score_achieved) FROM stdin;
1	1	2023-01-10 10:30:00	90
2	2	2023-01-20 11:30:00	85
3	3	2023-02-01 12:30:00	80
4	4	2023-02-10 13:30:00	95
5	5	2023-03-01 14:30:00	70
1	3	2024-07-08 01:30:36.042145	2
\.


--
-- TOC entry 4940 (class 0 OID 17427)
-- Dependencies: 244
-- Data for Name: user_achievement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_achievement (user_id, achievement_id, date_achieved) FROM stdin;
1	2	2024-07-23
1	3	2024-07-23
1	4	2024-07-23
1	5	2024-07-23
\.


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 242
-- Name: achievement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.achievement_id_seq', 30, true);


--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 221
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 449, true);


--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 223
-- Name: course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_id_seq', 411, true);


--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 240
-- Name: course_material_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_material_id_seq', 42, true);


--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 219
-- Name: direction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.direction_id_seq', 468, true);


--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 217
-- Name: institution_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.institution_id_seq', 286, true);


--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 227
-- Name: lesson_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lesson_id_seq', 74, true);


--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 233
-- Name: quiz_answer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quiz_answer_id_seq', 105, true);


--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 229
-- Name: quiz_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quiz_id_seq', 136, true);


--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 231
-- Name: quiz_question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quiz_question_id_seq', 94, true);


--
-- TOC entry 4956 (class 0 OID 0)
-- Dependencies: 238
-- Name: review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.review_id_seq', 55, true);


--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 215
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 276, true);


--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 225
-- Name: site_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.site_user_id_seq', 225, true);


-- Completed on 2024-08-05 19:08:25

--
-- PostgreSQL database dump complete
--

