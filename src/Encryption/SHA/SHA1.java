package Encryption.SHA;

import Binary.Binary;
import Common.Messages;

public class SHA1 {
    public SHA1() {

    }

    public Binary GetHash(String message) throws Exception {
        return GetHash(new Binary[]{
                new Binary("0x67452301", 16).ToNBit(32),
                new Binary("0xEFCDAB89", 16).ToNBit(32),
                new Binary("0x98BADCFE", 16).ToNBit(32),
                new Binary("0x10325476", 16).ToNBit(32),
                new Binary("0xC3D2E1F0", 16).ToNBit(32),
        }, message);

    }

    //https://github.com/opendatakit/javarosa/blob/master/src/org/javarosa/core/util/SHA1.java
    private Binary GetHash(Binary values[], String message) throws Exception {
        if (values.length == 5) {
            Binary binaryMessage = new Binary();
            for (int i = 0; i < message.length(); i++) {
                //перевод в байтовый формат
                Binary binary = new Binary((long) message.charAt(i)).ToNBit(8);
                binaryMessage = binaryMessage.Add(binary);
            }

//            System.out.println("Исходное сообщение");
//            System.out.println(binaryMessage);

            int oldLength = binaryMessage.GetLength();
            //TODO:Предварительная обрботка
            binaryMessage = binaryMessage.Add(true);
            while (binaryMessage.GetLength() % 512 != 448)
                binaryMessage = binaryMessage.Add(false);


            binaryMessage = binaryMessage.Add(new Binary(oldLength));

//            System.out.println("Предварительная обрабока");
//            System.out.println(binaryMessage);

            //TODO:В процессе сообщение разбивается последовательно по 512 бит
            Binary[] parts512 = binaryMessage.GetBits(512);
            for (Binary part : parts512) {
                Binary[] parts32 = part.GetBits(32);
                Binary[] subParts32 = new Binary[80];
                Binary a = values[0];
                Binary b = values[1];
                Binary c = values[2];
                Binary d = values[3];
                Binary e = values[4];
                for (int i = 0; i < 80; i++) {
                    if (i < 16)
                        subParts32[i] = parts32[i];
                    else
                        subParts32[i] = subParts32[i - 3].XOR(subParts32[i - 8]).XOR(subParts32[i - 14]).XOR(subParts32[i - 16]).LeftShift();
                    Binary f = new Binary().ToNBit(32), k = new Binary().ToNBit(32), temp;
                    if (i >= 0 && i <= 19) {
                        f = b.AND(c).OR(b.NOT().AND(d));
                        k = new Binary("0x5A827999", 16).ToNBit(32);
                    } else if (i >= 20 && i <= 39) {
                        f = b.XOR(c).XOR(d);
                        k = new Binary("0x6ED9EBA1", 16).ToNBit(32);
                    } else if (i >= 40 && i <= 59) {
                        f = b.AND(c).OR((b.AND(d))).OR((c.AND(d)));
                        k = new Binary("0x8F1BBCDC", 16).ToNBit(32);
                    } else if (i >= 60 && i <= 79) {
                        f = b.XOR(c).XOR(d);
                        k = new Binary("0xCA62C1D6", 16).ToNBit(32);
                    }
                    temp = a.LeftShift(5).Plus(f).Plus(e).Plus(k).Plus(subParts32[i]).ToNBit(32);
                    e = d;
                    d = c;
                    c = b.LeftShift(30);
                    b = a;
                    a = temp;

//                    System.out.println("a:" + a);
//                    System.out.println("b:" + b);
//                    System.out.println("c:" + c);
//                    System.out.println("d:" + d);
//                    System.out.println("e:" + e);
                }
                values[0] = values[0].Plus(a).ToNBit(32);
                values[1] = values[1].Plus(b).ToNBit(32);
                values[2] = values[2].Plus(c).ToNBit(32);
                values[3] = values[3].Plus(d).ToNBit(32);
                values[4] = values[4].Plus(e).ToNBit(32);

            }
            //TODO:Итоговое хеш-значение(h0, h1, h2, h3, h4):
            return values[0].Add(values[1].Add(values[2].Add(values[3].Add(values[4]))));
        } else
            throw new Exception(Messages.Exceptions.sizeIncorrect);
    }
}
