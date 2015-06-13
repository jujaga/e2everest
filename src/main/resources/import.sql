-- Header
INSERT INTO CLINIC VALUES(123456,NULL,NULL,NULL,NULL,NULL,NULL,'McMaster Hospital',NULL,NULL,NULL,NULL);
INSERT INTO DEMOGRAPHIC VALUES(1,'1234 Street',NULL,NULL,NULL,NULL,NULL,'city',NULL,NULL,'25',NULL,'test@test.com',NULL,NULL,'John',NULL,NULL,'448000001','Cleese',NULL,NULL,'09',NULL,NULL,'English',NULL,NULL,NULL,'1234567','765-4321','V8T1D6',NULL,'999998','BC',NULL,NULL,NULL,NULL,'M',NULL,NULL,NULL,NULL,NULL,'1940'),(2,'404 Street',NULL,NULL,NULL,NULL,NULL,'city',NULL,NULL,'13',NULL,'test@test.com',NULL,NULL,'Missing',NULL,NULL,'448000001','Subject',NULL,NULL,'06',NULL,NULL,'English',NULL,NULL,NULL,'1234567','765-4321','V2T9E1',NULL,'999998','BC',NULL,NULL,NULL,NULL,'F',NULL,NULL,NULL,NULL,NULL,'1970');
INSERT INTO PROVIDER VALUES(999998,NULL,NULL,NULL,NULL,NULL,'test2@test2.com','oscar',NULL,'oscardoc',NULL,NULL,NULL,'3456789','cpsid',NULL,NULL,NULL,'M',NULL,NULL,NULL,NULL,'987-6543');

-- Allergies
INSERT INTO ALLERGIES VALUES(1,NULL,NULL,NULL,FALSE,1,'PENICILLINS, COMBINATIONS WITH OTHER ANTIBACTERIAL','43507','2013-09-26',NULL,NULL,'2013-03-05 13:30:47',NULL,'4',0,NULL,NULL,NULL,'4','1935-01-01',8);

-- Clinically Measured Observations
INSERT INTO MEASUREMENTS VALUES(1,0,NULL,'2013-09-25 15:51:13','130/85','2013-09-25 00:00:00',1,'sitting position','999998','BP');
INSERT INTO MEASUREMENTS VALUES(2,0,NULL,'2013-09-25 15:51:13','187','2013-09-25 00:00:00',1,'in cm','999998','HT');
INSERT INTO MEASUREMENTS VALUES(3,0,NULL,'2013-09-25 15:51:13','85','2013-09-25 00:00:00',1,'in bpm (nnn) Range:40-180','999998','HR');
INSERT INTO MEASUREMENTS VALUES(4,0,NULL,'2013-09-25 15:51:13','37','2013-09-25 00:00:00',1,'degrees celcius','999998','TEMP');
INSERT INTO MEASUREMENTS VALUES(5,0,NULL,'2013-09-25 15:51:13','92','2013-09-25 00:00:00',1,'Waist Circum in cm','999998','WAIS');
INSERT INTO MEASUREMENTS VALUES(6,0,NULL,'2013-09-25 15:51:13','95','2013-09-25 00:00:00',1,'in kg','999998','WT');

-- Encounters
INSERT INTO CASEMGMT_NOTE VALUES(1,0,0,'',1,'','[25-Sep-2013 .: Tel-Progress Notes]',NULL,NULL,1,0,NULL,NULL,'[25-Sep-2013 .: Tel-Progress Notes]','2013-09-25 15:50:33',NULL,0,'10016','999998','1','0',1,'999998','2013-09-25 15:50:33','2da90304-4809-4777-a6d7-c8eb0fcc3698');
INSERT INTO CASEMGMT_NOTE VALUES(2,0,0,'',1,'','[25-Sep-2013 .: Tel-Progress Notes]\nBP    130/85 sitting position \nHT    187 in cm \nHR    85 in bpm (nnn) Range:40-180 \nTEMP    37 degrees celcius \nWAIS    92 Waist Circum in cm \nWT    95 in kg\n   ----------------History Record----------------   \n[25-Sep-2013 .: Tel-Progress Notes]\n',NULL,NULL,1,0,NULL,NULL,'[25-Sep-2013 .: Tel-Progress Notes]\nBP    130/85 sitting position \nHT    187 in cm \nHR    85 in bpm (nnn) Range:40-180 \nTEMP    37 degrees celcius \nWAIS    92 Waist Circum in cm \nWT    95 in kg','2013-09-25 15:50:00',NULL,0,'10016','999998','1','0',1,'999998','2013-09-25 15:51:23','2da90304-4809-4777-a6d7-c8eb0fcc3698');
INSERT INTO CASEMGMT_NOTE VALUES(6,0,0,'',1,'','',NULL,NULL,1,0,NULL,NULL,'Situational Crisis','2013-09-26 16:18:23',NULL,0,'10016','999998','1','0',1,'999998','2013-09-26 16:18:23','481d5e06-5854-4a04-8ae3-6be35f0b7176');
INSERT INTO CASEMGMT_NOTE VALUES(7,0,0,'',1,'','',NULL,NULL,1,0,NULL,NULL,'Vitamin D3','2013-09-26 16:19:01',NULL,2,'10016','999998','1','0',1,'999998','2013-09-26 16:19:01','604ee129-a4e0-4efc-b508-98c9911cde2f');
INSERT INTO CASEMGMT_NOTE VALUES(8,0,0,'',1,'','',NULL,NULL,1,0,NULL,NULL,'Vitamin C','2013-09-26 16:19:59',NULL,1,'10016','999998','1','0',1,'999998','2013-09-26 16:19:59','bc185582-c00b-4fd8-ad2a-3918e2274110');
INSERT INTO CASEMGMT_NOTE VALUES(9,0,0,'',1,'','',NULL,NULL,1,0,NULL,NULL,'Ginseng Tincture','2013-09-26 16:20:10',NULL,0,'10016','999998','1','0',1,'999998','2013-09-26 16:20:10','20c8f109-a40b-4e4f-a222-9464d2d2cfff');
INSERT INTO CASEMGMT_NOTE VALUES(10,0,0,'',1,'','',NULL,NULL,1,0,NULL,NULL,'Heart Attack','2013-09-26 16:20:35',NULL,0,'10016','999998','1','0',1,'999998','2013-09-26 16:20:35','de752b1b-9eb5-451d-870b-6e2a59f8d055');

-- Immunizations
INSERT INTO PREVENTIONS VALUES(1,'2013-09-27 14:01:22','999998','0',1,'2013-09-27 14:01:22','0','2015-06-10','2012-09-01','Td','999998',NULL,'0');
INSERT INTO PREVENTIONS VALUES(2,'2013-09-27 14:01:44','999998','0',1,'2013-09-27 14:01:44','0',NULL,'2009-02-01','Flu','999998',NULL,'1');
INSERT INTO PREVENTIONS VALUES(3,'2013-09-27 14:02:19','999998','0',1,'2013-09-27 14:02:19','0',NULL,'2012-10-31','Pneumovax','999998',NULL,'2');
INSERT INTO PREVENTIONSEXT VALUES(1,'location',1,'clinic'),(2,'lot',1,'1234'),(3,'route',1,'left deltoid'),(4,'dose',1,NULL),(5,'comments',1,'comment'),(6,'neverReason',1,'test'),(7,'manufacture',1,NULL),(8,'name',1,NULL);
INSERT INTO PREVENTIONSEXT VALUES(9,'location',2,NULL),(10,'lot',2,NULL),(11,'route',2,NULL),(12,'dose',2,NULL),(13,'comments',2,NULL),(14,'neverReason',2,'allergic'),(15,'manufacture',2,NULL),(16,'name',2,NULL);
INSERT INTO PREVENTIONSEXT VALUES(17,'location',3,'hospital'),(18,'lot',3,NULL),(19,'route',3,'right deltoid'),(20,'dose',3,NULL),(21,'comments',3,NULL),(22,'neverReason',3,NULL),(23,'manufacture',3,NULL),(24,'name',3,NULL);

-- Labs
INSERT INTO HL7TEXTINFO VALUES(9,'13-999955528','HAEM1/HAEM3/CHEM4/CHEM29/REFER1',NULL,128,'APATIENT','9055555555',9,NULL,'EXCELLERIS','2013-05-31 10:20:12',NULL,'F','BOB MDCARE','A',NULL,'M');
INSERT INTO MEASUREMENTS VALUES(26,0,NULL,'2013-09-26 03:56:15','158','2013-05-31 10:20:12',1,NULL,'0',NULL);
INSERT INTO MEASUREMENTS VALUES(31,0,NULL,'2013-09-26 03:56:15','12.6','2013-05-31 10:20:12',1,NULL,'0',NULL);
INSERT INTO MEASUREMENTS VALUES(40,0,NULL,'2013-09-26 03:56:15','68','2013-05-31 10:20:12',1,'in umol/L','999998','SCR');
INSERT INTO MEASUREMENTS VALUES(41,0,NULL,'2013-09-26 03:56:15','113','2013-05-31 10:20:12',1,'in ml/min','999998','EGFR');
INSERT INTO MEASUREMENTSEXT VALUES(172,'lab_no',26,'9'),(173,'abnormal',26,'N'),(174,'identifier',26,'718-7'),(175,'name',26,'Hemoglobin'),(176,'labname',26,'LIFELABS'),(177,'accession',26,'13-999955528'),(178,'request_datetime',26,'2013-05-27 13:40:00'),(179,'datetime',26,'2013-05-31 10:20:12'),(180,'olis_status',26,'F'),(181,'unit',26,'g/L'),(182,'minimum',26,'133'),(183,'other_id',26,'0-0'),(184,'comments',26,'result notes');
INSERT INTO MEASUREMENTSEXT VALUES(231,'lab_no',31,'9'),(232,'abnormal',31,'N'),(233,'identifier',31,'788-0'),(234,'name',31,'RDW'),(235,'labname',31,'LIFELABS'),(236,'accession',31,'13-999955528'),(237,'request_datetime',31,'2013-05-27 13:40:00'),(238,'datetime',31,'2013-05-31 10:20:12'),(239,'olis_status',31,'F'),(240,'unit',31,'%'),(241,'minimum',31,'11.5'),(242,'other_id',31,'0-1');
INSERT INTO MEASUREMENTSEXT VALUES(338,'lab_no',40,'9'),(339,'abnormal',40,'A'),(340,'identifier',40,'14682-9'),(341,'name',40,'Creatinine'),(342,'labname',40,'LIFELABS'),(343,'accession',40,'13-999955528'),(344,'request_datetime',40,'2013-05-27 13:40:00'),(345,'datetime',40,'2013-05-31 10:20:12'),(346,'olis_status',40,'F'),(347,'unit',40,'umol/L'),(348,'minimum',40,'70'),(349,'other_id',40,'1-0');
INSERT INTO MEASUREMENTSEXT VALUES(350,'lab_no',41,'9'),(351,'abnormal',41,'N'),(352,'identifier',41,'33914-3'),(353,'name',41,'Estimated GFR'),(354,'labname',41,'LIFELABS'),(355,'accession',41,'13-999955528'),(356,'request_datetime',41,'2013-05-27 13:40:00'),(357,'datetime',41,'2013-05-31 10:20:12'),(358,'olis_status',41,'F'),(359,'unit',41,'mL/min'),(360,'range',41,'>=60');
INSERT INTO PATIENTLABROUTING VALUES(27,'2013-09-26 00:00:00',NULL,1,9,'HL7');

-- Medications
INSERT INTO DRUGS VALUES(1,FALSE,NULL,NULL,'C09AA05','AVA-RAMIPRIL 5MG',NULL,'2013-09-27 12:51:23',FALSE,NULL,FALSE,1,0,'5.0 MG','TABLET','D','28',NULL,'2013-11-22','OD','6227','RAMIPRIL',FALSE,NULL,NULL,'2013-09-27 12:51:23',TRUE,'Take',FALSE,FALSE,NULL,NULL,FALSE,NULL,NULL,1,FALSE,'999998','28',0,0,'02363283',1,'PO','2013-09-27',NULL,1,'AVA-RAMIPRIL 5MG\nTake 1 PO OD 28 days\nQty:28 Repeats:1','special instruction',FALSE,1,1,'MG',NULL,'2013-09-27');
INSERT INTO DRUGS VALUES(2,FALSE,NULL,NULL,'C03DA01','SPIRONOLACTONE 25MG TABLET',NULL,'2013-09-27 12:51:23',FALSE,NULL,FALSE,1,0,'25.0 MG','TABLET','D','28',NULL,'2013-11-22','QAM','63449','SPIRONOLACTONE',FALSE,NULL,NULL,'2013-09-27 12:51:23',FALSE,'Take',FALSE,FALSE,NULL,NULL,FALSE,NULL,NULL,2,FALSE,'999998','28',0,0,'XX613215',1,'PO','2013-09-27',NULL,1,'SPIRONOLACTONE 25MG TABLET\nTake 1 PO QAM 28 days\nQty:28 Repeats:1','special instruction',FALSE,1,1,'MG',NULL,'2013-09-27');
INSERT INTO DRUGS VALUES(3,FALSE,NULL,NULL,'C09AA05','AVA-RAMIPRIL 5MG',NULL,'2014-09-27 12:51:23',FALSE,NULL,FALSE,1,0,'5.0 MG','TABLET','D','28',NULL,'2014-11-22','OD','6227','RAMIPRIL',FALSE,NULL,NULL,'2014-09-27 12:51:23',TRUE,'Take',FALSE,FALSE,NULL,NULL,FALSE,NULL,NULL,1,FALSE,'999998','28',0,0,'02363283',1,'PO','2014-09-27',NULL,1,'AVA-RAMIPRIL 5MG\nTake 1 PO OD 28 days\nQty:28 Repeats:1','special instruction',FALSE,1,1,'MG',NULL,'2014-09-27');

-- Problem List
INSERT INTO DXRESEARCH VALUES(1,0,'icd9',1,'428','2013-09-26','A','2013-09-26 00:00:00');
INSERT INTO DXRESEARCH VALUES(2,0,'icd9',1,'401','2013-09-26','A','2013-09-26 00:00:00');
