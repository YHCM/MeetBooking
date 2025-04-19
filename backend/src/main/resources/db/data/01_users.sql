DELETE FROM users;

-- 密码就是对应的角色小写
-- 插入 ROOT 用户（使用 root_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR root_seq, 'root', '$2a$10$uLbCvSxgSsTStJurm58Hber2jViqIjTwjFAhGP/IHwRBNJpvodjKa', 'ROOT');

-- 插入 ADMIN 用户（使用 admin_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR admin_seq, 'admin', '$2a$10$vd9BtirJh6F.vphjwPSEIOc13BUoNeoTTx3VMdQc5K4cq6zi1t1hG', 'ADMIN');

-- 插入 STAFF 用户（使用 staff_seq）
INSERT INTO users (user_id, username, password, role)
VALUES (NEXT VALUE FOR staff_seq, 'staff', '$2a$10$Tlfg/uzVpbyIihFwrdgkU.t3E6CeUIZD99BC5qh09r89yJtuFFgci', 'STAFF');

-- 插入 CLIENT 用户（使用 client_seq）
INSERT INTO users (user_id, username, password, role, company_name, phone_number)
VALUES (NEXT VALUE FOR client_seq, 'client', '$2a$10$EnTQaiXERPXNHdDMz9.B1ueWSN17zaIFzywglwpqQRGSe/UTWiLIW', 'CLIENT', 'company', '1234567890');



-- 添加数据（密码默认是 123456）
-- 添加 ADMIN，5 个
INSERT INTO users (user_id, username, password, role) VALUES
(NEXT VALUE FOR admin_seq, 'admin1', '$2a$10$3DYx.PICxqV8B4ON1ez.YOuta8uU1AZX8L/r.FK8jkd0CmZmqfb/m', 'ADMIN'),
(NEXT VALUE FOR admin_seq, 'admin2', '$2a$10$ZbrTx1lfPcvZ2e/ydM1b1OC1jJ3QjSIdRAE.XqSHcFSqMLe9RVkxe', 'ADMIN'),
(NEXT VALUE FOR admin_seq, 'admin3', '$2a$10$KN06w4P8AJXRGQSNrxN6/.uPOZ3erJW.AemYzRMNfFWTh/P5m.41G', 'ADMIN'),
(NEXT VALUE FOR admin_seq, 'admin4', '$2a$10$N0YierA.r8pM93C8izv/Keb66ctCNA6B3WozZ/OtV0Irpou6WQ8y2', 'ADMIN'),
(NEXT VALUE FOR admin_seq, 'admin5', '$2a$10$WnHtq1XWlpWjHeQZHfDYTeBpsPSRV.uNLXoRMrC7FE4/yV8fST9HK', 'ADMIN');

-- 添加 STAFF，50 个
INSERT INTO users (user_id, username, password, role) VALUES
(NEXT VALUE FOR staff_seq, 'staff1', '$2a$10$.CEiOIhzp/c.bNwBbh.nBOsLQ1jH21Z8wfOawf484nIXJHkLCPN9q', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff2', '$2a$10$jSOvxWPEuxRLshMg2FkmvejMfU/DOE3oYxwDM8HTZ3YsDGR6zzIMG', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff3', '$2a$10$2LaH4ODFKJokyLeosFNVOuniRb0h8TxEfODTrHXn3TbxAUQs9aqOO', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff4', '$2a$10$I.H56kVZuSF7E2qA3HcnyOnUptYj0BOZ1rGE4yVPqjvDTXWeYyWGu', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff5', '$2a$10$Mrfk1D4hGUjSm6GzF7jRI.6T7ZMLL1TBmVxSH6PlUnQEou8xGDWxi', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff6', '$2a$10$t1JD7SImcBP/X2XcN15Lb.X1RM3JaKLVAwbB/hTxbQdL3EHVzMhWO', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff7', '$2a$10$7Gb9aqVkw85.ce.4nXW.rerR0mCKwILgI9Lr3YcmHb1m0MG.tpt2C', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff8', '$2a$10$biVQ2Vyc0rEISjiDdu7dkutqEZmU3g1oVgbw.rJGnTZ3joBe0QyNq', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff9', '$2a$10$NMkTZaofn4/jAKXuYxIjj.Igf3ajspCHM4wrnml2gPiflnMkUs43a', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff10', '$2a$10$bLx9NDfqfc1waH.Nw0LKduaSpQRfFuSjzxVR1M5jTjJqc77EKMvXK', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff11', '$2a$10$HQabWoHRHF.39ZK8EntaWuOzbmXKTtHkrQSzVJX5pJ8.FWXgCon/K', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff12', '$2a$10$w11FC02X.kBpIOC1gKJ9.OHw52yIRecwNnEPgG.KDEmq4T/CbKO8y', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff13', '$2a$10$fNfDMIuLQfDND85f2VmZ.uM29.JulsorZWeCHBFAGZyfLFUHwK5Ui', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff14', '$2a$10$joMJpwftBniu4p7prQ7bc.fQOa1yggq19hscraeVfxkoDLZ2p0Y0u', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff15', '$2a$10$LzLjmGeakCsdYYsuFmfjRuRWaEAA7B4T0NhxDuMS4BYabyshnhJRS', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff16', '$2a$10$Mgm/wuSjQaqFJTNX3NprFeKGuQMNYgbT49UKAOKeWlXqnbLoNme46', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff17', '$2a$10$USzMGmMcEFZi7Y6dSjTkf.KwpLUn4NWuIBTOmvCRhxr/aooXOfx2G', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff18', '$2a$10$ZrwxP2x1g0ds9bej4Aq4Je1.JmHNwRRUUn3jpJlC9oWj.vBuFmCIC', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff19', '$2a$10$nujGlhC6jBS9S7C5rwHmzOhkoAQn1UYTpDt6UzWFCenafuksWQNm.', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff20', '$2a$10$heu6RPxXd4AFQrQkDJI4IOqxLdxecLkIej7LwAJxXX00Py85gx9em', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff21', '$2a$10$W7G1wBppdBBYaw2KlV/iRezm7sXPA0puHIMh1Hm6O3N8S.dZKkP3K', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff22', '$2a$10$uz5nYwBSk.Oi0izfz1wpceSI1u/7kiOWnWlLoj4diAc9je4nzuszW', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff23', '$2a$10$mefZxf3A9ldlFN3tk2tp5u8fOE31DyBabSY0FNOXWQU7l/XpXCDC.', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff24', '$2a$10$xd/Llt27.pr/9SQcScs.Gei0r8.HPL1iYy7JOj39fPQu9/3oHXHvC', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff25', '$2a$10$phu1r8elOzypHA04Vr1eE.UDu9qyZsI9ztcmTlinhwIgXItO3WQXi', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff26', '$2a$10$NptT.iEj0wRsCHKJzYFG2e5qumQ/c.cA7fLjcECewcMIETS8R9J.S', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff27', '$2a$10$1/4Ps0Ov7I.zKKjjfM7EcuTZLrfxUD0qslkdFeg0MHNVemxaRA.8S', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff28', '$2a$10$TgXJj9rJqWb6l4XXpYVTpuUMOsvOQZCgQiGNUWiH207wceJOBVEUK', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff29', '$2a$10$rSQyRmBDr/jfQchwqUAoruUqp.L4HebSxFdyZ/AnUasIXKX4lyw5K', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff30', '$2a$10$GDScG5KCfvGRfPc02zelMOKEa9c2n1X39mJxnt2tp/l.g.0xyFLVq', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff31', '$2a$10$MDudTkxsOO7DN//IMDhKgemEN3NMCl0EtX2r/qTUZfHQhfvKCi4IW', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff32', '$2a$10$iS08T3JeS1/aoxUWmOVECeduAZp.DWcRrWBAEMlHlj5Zg5zgX0oty', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff33', '$2a$10$ffZN9RK8k13W8NWco/eBGeVvDRDTIOwy4EeAqjW0AQesVfEcJXCWW', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff34', '$2a$10$W/28DwJdpG3X7RAwT2QRMuxgvOgUDFhxU6bsB4AdBB17xc1UrLb26', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff35', '$2a$10$LW6MJtmFG7GrSSY6pwZJG.0w0MzEGyx3fCZIx7dkV4awgL3UoJU3K', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff36', '$2a$10$hLTv3q1xgS05FvKTVni6VuKDVg4l6oMz3t9d91v4.6OWVzTOu70FK', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff37', '$2a$10$ZD2MnLQC1dmGNjZsw5jyWObXhviuMtPLulv1A1iu.RZIkCYPVz2dy', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff38', '$2a$10$aHv4.MBxiNuAaVo4WFOxMOhby4q8Ykg6zSUGB64hKbvOZqMpDtHgy', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff39', '$2a$10$4DHPj0fDluu9m7o62qRrTOM4vIgX1jHTPhauqMPl6VIhpadv60G3a', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff40', '$2a$10$iffV2CWuaztuGWk9sq6zvObW1bGvhpVgljiMlba2QMn2W9tBffMuu', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff41', '$2a$10$Bh1Y6Gwrsa8eqLZBaiPaKuXqdOg6I5wmIeu2vkFOHhi4Y0Q.Wm73y', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff42', '$2a$10$nzRr4.7BaKXbOH9Z1ViaQO4mfK3QVFpez5yL8ZyvtYhQSX8.7zLL2', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff43', '$2a$10$9ii6MkZNEoGUzEZPjjW1E.dDzw/laGPB2C45/gkl.yrggzJFAeBK2', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff44', '$2a$10$I3abCMzzanL10ZuAkI36ZejlMTrXDdkkzqKO4n9gUR1Dy6krGX05K', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff45', '$2a$10$NNkbDzyXCwCJFN5sL2H3uOWEvZb8MUD.KmohapinSF7O9EAOhE6UK', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff46', '$2a$10$U2IVGZ.Hf8tXUjuKSKpJ4u4TlDQA1/G/5svuKU.MC27wxJSdL4WLy', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff47', '$2a$10$IS4TMl7xQuXDJcVVchsbNucrNitVXGRj/wGkbJ0pl6FQU4Ukcf0e.', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff48', '$2a$10$Xfe6Iu2h/dDikruS0U3FpuO48mWerG8NjQuPUZQXYFpM/IOKhP8bG', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff49', '$2a$10$muEES9U7Tti28E4WmdCNJOsay67H.0p1QHasyuPlIdwZPLoX7fzAm', 'STAFF'),
(NEXT VALUE FOR staff_seq, 'staff50', '$2a$10$JN52upR/OCGetAbUEE49GOrhGUVxLqBfGyNRAeur6FbhJo0dVRf9S', 'STAFF');

-- 添加 CLIENT，50 个
INSERT INTO users (user_id, username, password, role, company_name, phone_number) VALUES
(NEXT VALUE FOR client_seq, 'client1', '$2a$10$6QbX8gZgJM7lKNEBwgNPsO5gOIIwP/mEP07kpWVpcxQQ2JTStw48u', 'CLIENT', 'company1', '19597586414'),
(NEXT VALUE FOR client_seq, 'client2', '$2a$10$aYuyLhgPnWwkdPGH6QgfqOgshsYmAm3iw7mITj4S.GeybKvAPz8f.', 'CLIENT', 'company2', '16762874581'),
(NEXT VALUE FOR client_seq, 'client3', '$2a$10$otgsSkxfIS49bgawFlsK/.loyWfHLjnZ0Sg7oEVBofNumc3wbCKR2', 'CLIENT', 'company3', '13320175770'),
(NEXT VALUE FOR client_seq, 'client4', '$2a$10$GX3SQ5WrxOf2CA8n9plWmOzs3l4Ak39UNvY0cB1x3iz.qtrhzWNKW', 'CLIENT', 'company4', '13661472294'),
(NEXT VALUE FOR client_seq, 'client5', '$2a$10$B/EWgfmDkm6EYe4hyVXGP.pLD197AMgGit0DMHlCKSnUlOsdaXUim', 'CLIENT', 'company5', '18396329987'),
(NEXT VALUE FOR client_seq, 'client6', '$2a$10$ugG70p4wp2S5Wkninf5eCO0301G2n2dXydC2pAxv4CDdZBcLmxbzm', 'CLIENT', 'company6', '17771168690'),
(NEXT VALUE FOR client_seq, 'client7', '$2a$10$CkFSYwZsE1n6lnRytFSpHeY9B3tD2vR/h9ejm6rrnlflVPEEg5/mi', 'CLIENT', 'company7', '17899109780'),
(NEXT VALUE FOR client_seq, 'client8', '$2a$10$ibdRl9EiR8f3f1LrXowJtOihJvp.qLmAM.RIP//mobV9u4ZnhSM5W', 'CLIENT', 'company8', '18257195594'),
(NEXT VALUE FOR client_seq, 'client9', '$2a$10$9j954srrg4FOpxxOe.wn1.NKdSD/f5eOVqHgbcHxPQzItfRvXv7gW', 'CLIENT', 'company9', '19908008389'),
(NEXT VALUE FOR client_seq, 'client10', '$2a$10$yw6nVgRvdH8GS9EjoVW1d.ev9CYsWglWbBsjPuqDykgT/RkGbZbYG', 'CLIENT', 'company10', '13857304007'),
(NEXT VALUE FOR client_seq, 'client11', '$2a$10$yRkBtfUGtRwZMWvKJxZup.sLm4gG2FK8hwr2GQN62w.2YgIyhVdla', 'CLIENT', 'company11', '13798389550'),
(NEXT VALUE FOR client_seq, 'client12', '$2a$10$IV4QcGLa14VOfG9eXv1zCeOjkKYRappz5YzOOqPZ.TPGwK0MwEX46', 'CLIENT', 'company12', '17197401548'),
(NEXT VALUE FOR client_seq, 'client13', '$2a$10$0X9jqXKrX5lhj3cqCHn2/OMrOaPSPvD7f4x2KNI7a/c/sjNBaTe4y', 'CLIENT', 'company13', '14700075112'),
(NEXT VALUE FOR client_seq, 'client14', '$2a$10$j2T3yHqijhsLYcnrSAp4/eSyGVaOvS8Wxvw.cJZeo4o7FDbkVUQsq', 'CLIENT', 'company14', '14949900110'),
(NEXT VALUE FOR client_seq, 'client15', '$2a$10$qDwxq5roQDTooELsBJlUoeuOw4l.jqOCQT9/.9qwquop3EUVUKgC6', 'CLIENT', 'company15', '17513726679'),
(NEXT VALUE FOR client_seq, 'client16', '$2a$10$EC/HAlcttekg1XWDTsHAEuA38Oixez4VM6CF/3U0Rcbsr/2SAp/6S', 'CLIENT', 'company16', '14750758371'),
(NEXT VALUE FOR client_seq, 'client17', '$2a$10$lAqG5EDhSmRLtFTPku.Nm.uFbRu9yG8ns99YsxhlGWcNFsb06owqO', 'CLIENT', 'company17', '19381936762'),
(NEXT VALUE FOR client_seq, 'client18', '$2a$10$SnReUU1CyKtVS29BzAP2.ufE1tK1bCsl9xaMlsfYHwZpZkTZxA8pe', 'CLIENT', 'company18', '15640608382'),
(NEXT VALUE FOR client_seq, 'client19', '$2a$10$e.JqsyQmJmGZTD5LUc/l4OYV79xiANKXf3dXGueUhvLwQ3fp9Y.6a', 'CLIENT', 'company19', '16739364575'),
(NEXT VALUE FOR client_seq, 'client20', '$2a$10$ZkeFXrtEknShO3xqaEWUyech0A.mQZkY.bFzGNpUPkLAK1aDQKLP.', 'CLIENT', 'company20', '14516849939'),
(NEXT VALUE FOR client_seq, 'client21', '$2a$10$PNk66tb6d0EFTK4tcaU5FOXyRiAp1N6nUk.bGA9fPMEIkdjB3uz6u', 'CLIENT', 'company21', '17496707577'),
(NEXT VALUE FOR client_seq, 'client22', '$2a$10$Cab7Hl5XXnw5VATQ9k9/vuTMWBn1y19TduhOTYkz3c9dGO5eh/IjK', 'CLIENT', 'company22', '18854082846'),
(NEXT VALUE FOR client_seq, 'client23', '$2a$10$p1EDZ5crM1Jc14pOuWlk2euJVsYbPgS64//QrLdHuio3yXdqOeHoy', 'CLIENT', 'company23', '17301142826'),
(NEXT VALUE FOR client_seq, 'client24', '$2a$10$Kn6Kh5p7nnuCZOkhv/yEmuklKWhdRvDjJlfSEX8tFR1H02wmItRPG', 'CLIENT', 'company24', '13748341937'),
(NEXT VALUE FOR client_seq, 'client25', '$2a$10$leXhb8uPC5Y3vK4c40XYs.V9txg7CX4yy1CrNMPLZvFg8gtIMUi5S', 'CLIENT', 'company25', '13878367431'),
(NEXT VALUE FOR client_seq, 'client26', '$2a$10$Cu0UoduckdeucRB7R59EtOjE8LP/WLRcOVEHv2Og72s6marmt0UTO', 'CLIENT', 'company26', '18070531286'),
(NEXT VALUE FOR client_seq, 'client27', '$2a$10$Fj0sgn8L.L3bYPuX6ErQu.50bIqLLYXIpRCIrnKjgbFtGg3BBj9p.', 'CLIENT', 'company27', '13495087823'),
(NEXT VALUE FOR client_seq, 'client28', '$2a$10$jcQlV6dnDfKhj1O9bJxp7.IEHbQ3jbe4TUT/i25d7nJK7yhZURgYy', 'CLIENT', 'company28', '18647125107'),
(NEXT VALUE FOR client_seq, 'client29', '$2a$10$sohFlqO/4UHpB3fvAtcVHuJDifcFlB571sFrB23hIJJQJrWVZ.UgW', 'CLIENT', 'company29', '18772203822'),
(NEXT VALUE FOR client_seq, 'client30', '$2a$10$FY5Q/yeRFT3PaCkrk3RnIeSbaNEoFkKKoqRSlDvnaeWcP16uvXe5i', 'CLIENT', 'company30', '18980270575'),
(NEXT VALUE FOR client_seq, 'client31', '$2a$10$lRjcs8Ets2COaRvznnsYAO/vZdTTQPdvnIqKogrYyqb1FsrDolby6', 'CLIENT', 'company31', '15705481971'),
(NEXT VALUE FOR client_seq, 'client32', '$2a$10$CXV.Y.LWQV4CZXhVJ0dDbe1DEJEZAnHmDK3VoA0rVdbbkycN4m1HW', 'CLIENT', 'company32', '14702897691'),
(NEXT VALUE FOR client_seq, 'client33', '$2a$10$obclFS5h8X6RvyNKwNFzxOl6zvHqy1yUtDUkvcKvpmGA.QNHPDGsK', 'CLIENT', 'company33', '17151084424'),
(NEXT VALUE FOR client_seq, 'client34', '$2a$10$bRQKUmdqRu7QOOHQP4GAx.1w9nP0ecKmscDPtD9Bion8nFGmFSaju', 'CLIENT', 'company34', '19779110603'),
(NEXT VALUE FOR client_seq, 'client35', '$2a$10$5OrDrIk2wGIXGaPyB8WeNe1CiaS2J4On523bR3M/QmZnVMYDuPBja', 'CLIENT', 'company35', '17569303419'),
(NEXT VALUE FOR client_seq, 'client36', '$2a$10$zjx1MInqxzvuEgK7L4trpu4BrIIaycWCNL0jM1t7hXWJNoK261z5m', 'CLIENT', 'company36', '15621832912'),
(NEXT VALUE FOR client_seq, 'client37', '$2a$10$SU8GGYelY3OpSvO1ZNdm5OzcMROzHiLISeUzxWmXvQZwzQDV9DAxi', 'CLIENT', 'company37', '19166356722'),
(NEXT VALUE FOR client_seq, 'client38', '$2a$10$zX/GLdgUh2y8N9I9Ex1F5.D1MPezIReegi2jdl/F23Sg03KhPC8/K', 'CLIENT', 'company38', '17732417819'),
(NEXT VALUE FOR client_seq, 'client39', '$2a$10$Wfd9n8DMnE3h7/bU5Rsd5O.NBtY80F5QxEudQNfanzUuXaOcEUfWG', 'CLIENT', 'company39', '13061623275'),
(NEXT VALUE FOR client_seq, 'client40', '$2a$10$RqmfdES6RwzxJ9WT0Cn8k.p57J69E0UZewvsETWFRH3fq9IMXnsom', 'CLIENT', 'company40', '15876412524'),
(NEXT VALUE FOR client_seq, 'client41', '$2a$10$3nHrXgR97rvdD7Qp3eGrsO5t6m4RHUmHOV73yqi05w7SznOzrT74a', 'CLIENT', 'company41', '15124319391'),
(NEXT VALUE FOR client_seq, 'client42', '$2a$10$YCc0qjlvDtbuKMHLtvWAWuKBHwZp7ieUslxuCeWPRSP.w8z.I2hS6', 'CLIENT', 'company42', '18336649908'),
(NEXT VALUE FOR client_seq, 'client43', '$2a$10$jazSluFxMU6QOuA8PTwp/eJsPkEnEkTlIOI9aB8H8C5Wy7y9GlmW.', 'CLIENT', 'company43', '18001495125'),
(NEXT VALUE FOR client_seq, 'client44', '$2a$10$Ldb7xQ0jwa/6Gwi9EANR3euzeJC87.1qsco/LW4dJbX20Zdn8sA4.', 'CLIENT', 'company44', '14942157775'),
(NEXT VALUE FOR client_seq, 'client45', '$2a$10$FzfCbsUv77B3Benhwpwa4Ot58tHY/BizX3gaxFqUQhprPD.I4xxue', 'CLIENT', 'company45', '18315849255'),
(NEXT VALUE FOR client_seq, 'client46', '$2a$10$5ffhs.v7CZI08r.VdfEa5uEYeON9z3y92rRsi58OFXtAHPrTjvYbK', 'CLIENT', 'company46', '19640555864'),
(NEXT VALUE FOR client_seq, 'client47', '$2a$10$N.n/EAcftxAOZlpdm0JIYONjm6y/ax2rKpvgtT2AHQ3uJpkp54eXC', 'CLIENT', 'company47', '17461331765'),
(NEXT VALUE FOR client_seq, 'client48', '$2a$10$zj9lsyR6C9Bgb4IOsOMuXuE00hOsHVwhGUwnnuICTgZefnk2JBQfm', 'CLIENT', 'company48', '17547254302'),
(NEXT VALUE FOR client_seq, 'client49', '$2a$10$sl/F6pR.i1GM1Ex2u5Bsm.Jhz5SG8jhiEGdpjNy2cgUuRvYXxbMLO', 'CLIENT', 'company49', '13204426790'),
(NEXT VALUE FOR client_seq, 'client50', '$2a$10$FZmbM43/OnQ0BFGzWTa2IewECjG3a.s1ccbDpqkl1SrQpmYEx/Fgi', 'CLIENT', 'company50', '13731791950');
