package Binary;

import Common.Messages;
import Encryption.TruthTables.Tables;
import Helpers.Arrays;
import Helpers.Converter;

import java.util.HashMap;

/**
 * Двоичное число
 */
public class Binary {

    //<editor-fold desc="Fields">
    private Arrays<Boolean> helper = new Arrays<Boolean>();

    private Boolean[] bits;
    //</editor-fold>

    //<editor-fold desc="Construtors">
    public Binary() {
        this.bits = new Boolean[]{};
    }

    public Binary(Boolean[] bits) {
        this.bits = bits;
    }

    public Binary(String str, int base) throws Exception {
        if (base > 1) {
            if (base == 2) {
                this.bits = new Boolean[str.length()];
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == '0')
                        this.bits[i] = Boolean.FALSE;
                    else if (str.charAt(i) == '1')
                        this.bits[i] = Boolean.TRUE;
                    else
                        throw new Exception(Messages.Exceptions.valueIncorrect);
                }
            }
            if (base == 8) {
                ///
                ///
            }
            if (base == 16) {
                if (str.length() > 2 && str.charAt(0) == '0' && str.charAt(1) == 'x')
                    str = str.substring(2);
                Long decimal = Long.parseLong(str, base);
                this.bits = this.ToBin(Long.valueOf(decimal)).GetBits();
            }
        } else
            throw new Exception(Messages.Exceptions.valueIncorrect);
    }

    public Binary(Integer value) throws Exception {
        this.bits = this.ToBin(Long.valueOf(value)).GetBits();
    }

    public Binary(Long value) throws Exception {
        this.bits = this.ToBin(value).GetBits();
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public Boolean Get(int index) throws Exception {
        if (helper.Check(this.bits, index))
            return this.bits[index];
        else
            throw new Exception(Messages.Exceptions.indexIncorrect);
    }

    public Boolean[] Get(int index, int count) throws Exception {
        Boolean[] result = new Boolean[count];
        for (int i = 0; i < count; i++)
            result[i] = this.Get(index + i);
        return result;
    }

    public Boolean[] Get(int[] indexes) throws Exception {
        Boolean[] result = new Boolean[indexes.length];
        for (int i = 0; i < indexes.length; i++)
            result[i] = this.Get(indexes[i]);
        return result;
    }

    public Boolean[] GetBits() {
        Arrays<Boolean> helper = new Arrays<>();
        return helper.Copy(this.bits);
    }

    public Binary[] GetBits(int n) throws Exception {
        int count = (int) Math.floor(this.GetLength() / n);
        Binary[] result = new Binary[count];
        for (int i = 0; i < count; i++)
            result[i] = new Binary(this.Get(i * n, n));
        return result;
    }

    public Integer GetLength() {
        return this.bits.length;
    }

    public Binary GetHighest() {
        Boolean[] bits = new Boolean[this.GetLength() / 2];
        for (int i = 0; i < this.GetLength() / 2; i++)
            bits[i] = this.bits[i];
        return new Binary(bits);
    }

    public Binary GetLowest() {
        Boolean[] bits = new Boolean[this.GetLength() / 2];
        for (int i = 0; i < this.GetLength() / 2; i++)
            bits[i] = this.bits[i + this.GetLength() / 2];
        return new Binary(bits);
    }

    //</editor-fold>

    //<editor-fold desc="Setters">
    public void Set(int index, Boolean value) throws Exception {
        if (helper.Check(this.bits, index))
            this.bits[index] = value;
        else
            throw new Exception(Messages.Exceptions.indexIncorrect);

    }

    public void Set(int index, Integer value) throws Exception {
        if (value > -1 && value < 2)
            this.Set(index, value);
        else
            throw new Exception(Messages.Exceptions.valueIncorrect);
    }

    public void Set(int index, Boolean[] values) throws Exception {
        for (int i = 0; i < values.length; i++)
            this.Set(index + i, values[i]);
    }

    public void SetHighest(Binary part) throws Exception {
        if (part.GetLength() == this.GetLength() / 2) {
            Set(0, part.GetBits());
//            Boolean[] bits = this.GetBits();
//            for (int i = 0; i < part.GetLength(); i++)
//                bits[i] = part.Get(i);
//            return new Binary(bits);
        } else
            throw new Exception(Messages.Exceptions.sizeIncorrect);
    }

    public void SetLowest(Binary part) throws Exception {
        if (part.GetLength() == this.GetLength() / 2) {
            Set(this.GetLength() / 2, part.GetBits());
//            Boolean[] bits = this.GetBits();
//            for (int i = this.GetLength() / 2; i < this.GetLength(); i++)
//                bits[i] = part.Get(i);
//            return new Binary(bits);
        } else
            throw new Exception(Messages.Exceptions.sizeIncorrect);
    }
    //</editor-fold>

    //<editor-fold desc="Math Operations">
    public Binary Plus(Binary value) throws Exception {
        return new Binary(this.ToLong() + value.ToLong());
    }

    public Binary Minus(Binary value) throws Exception {
        return new Binary(this.ToLong() - value.ToLong());
    }

    public Binary Division(Binary value) throws Exception {
        return new Binary(this.ToLong() / value.ToLong());
    }

    public Binary Multiply(Binary value) throws Exception {
        return new Binary(this.ToLong() * value.ToLong());
    }
    //</editor-fold>

    //<editor-fold desc="Logic Operations">

    /**
     * Исключающее или
     *
     * @param value
     * @return
     * @throws Exception
     */
    public Binary XOR(Binary value) throws Exception {
        Integer length = Math.max(this.GetLength(), value.GetLength());
        Binary b1 = this.ToNBit(length);
        Binary b2 = value.ToNBit(length);
        Binary result = new Binary();
        result = result.ToNBit(length);
        for (int i = 0; i < length; i++)
            result.Set(i, Tables.XOR(b1.Get(i), b2.Get(i)));
        return result;
    }

    /**
     * Логическое И
     *
     * @param value
     * @return
     * @throws Exception
     */
    public Binary AND(Binary value) throws Exception {
        Integer length = Math.max(this.GetLength(), value.GetLength());
        Binary b1 = this.ToNBit(length);
        Binary b2 = value.ToNBit(length);
        Binary result = new Binary();
        result = result.ToNBit(length);
        for (int i = 0; i < length; i++)
            result.Set(i, Tables.AND(b1.Get(i), b2.Get(i)));
        return result;
    }

    /**
     * Логическое ИЛИ
     *
     * @param value
     * @return
     * @throws Exception
     */
    public Binary OR(Binary value) throws Exception {
        Integer length = Math.max(this.GetLength(), value.GetLength());
        Binary b1 = this.ToNBit(length);
        Binary b2 = value.ToNBit(length);
        Binary result = new Binary();
        result = result.ToNBit(length);
        for (int i = length - 1; i >= 0; i--)
            result.Set(i, Tables.OR(b1.Get(i), b2.Get(i)));
        return result;
    }

    /**
     * Инвертирует указанное число
     *
     * @return
     * @throws Exception
     */
    public Binary NOT() throws Exception {
        Binary result = new Binary(this.GetBits());
        for (int i = 0; i < result.GetLength(); i++)
            result.Set(i, !result.Get(i));
        return result;
    }

    /**
     * Циклический сдвиг влево
     *
     * @return
     */
    public Binary LeftShift() {
        Boolean[] result = this.GetBits();
        Boolean tmp = result[0];
        for (int i = 0; i < this.bits.length - 1; i++)
            result[i] = result[i + 1];
        result[this.bits.length - 1] = tmp;
        return new Binary(result);
    }

    /**
     * Циклический сдвиг влево
     *
     * @param n - количество сдвигов
     * @return
     */
    public Binary LeftShift(int n) {
        Binary result = new Binary(this.GetBits());
        for (int i = 0; i < n; i++)
            result = result.LeftShift();
        return result;
    }

    /**
     * Циклический сдвиг вправо
     *
     * @return
     */
    public Binary RightShift() {
        Boolean[] result = this.GetBits();
        Boolean tmp = result[result.length - 1];
        for (int i = this.bits.length - 1; i > 0; i--)
            result[i] = result[i - 1];
        result[0] = tmp;
        return new Binary(result);
    }

    /**
     * Циклический сдвиг вправо
     *
     * @param n - количество сдвигов
     * @return
     */
    public Binary RightShift(int n) {
        Binary result = new Binary(this.GetBits());
        for (int i = 0; i < n; i++)
            result = result.RightShift();
        return result;

    }

    /**
     * Добавляет один бит в конец бинарного числа
     *
     * @param bit бит
     * @return
     */
    public Binary Add(Boolean bit) throws Exception {
        Boolean[] bits = new Boolean[this.bits.length + 1];
        for (int i = 0; i < this.GetLength(); i++)
            bits[i] = this.Get(i);
        bits[bits.length - 1] = bit;
        return new Binary(bits);
    }

    public Binary Add(Binary value) throws Exception {
        Binary result = new Binary(this.GetBits());
        for (int i = 0; i < value.GetLength(); i++)
            result = result.Add(value.Get(i));
        return result;
    }

    /**
     * @param map
     * @return Делает перестановку по указанной таблице
     * @throws Exception
     */
    public Binary Swap(HashMap<Integer, Integer> map) throws Exception {
        Boolean[] bits = new Boolean[map.size()];
        for (int i = 0; i < map.size(); i++)
            bits[i] = this.Get(map.get(i));
        return new Binary(bits);
    }

    public Boolean IsEqual(Binary value) {
        if (this.bits.length != value.GetLength())
            return false;
        for (int i = 0; i < this.bits.length; i++)
            if (this.bits[i] != value.GetBits()[i])
                return false;
        return true;
    }

    //</editor-fold>

    //<editor-fold desc="Converters">
    public Binary ToNBit(int size) throws Exception {
        if (size > 0) {
            if (size == this.GetLength())
                return new Binary(this.bits);
            Boolean[] result = new Boolean[size];
            for (int i = 0; i < size; i++)
                result[i] = false;
            if (this.bits.length < size) {
                for (int i = 0; i < this.bits.length; i++)
                    result[result.length - 1 - i] = this.bits[this.bits.length - 1 - i];
            } else {
                for (int i = 0; i < size; i++)
                    result[size - 1 - i] = this.bits[this.bits.length - 1 - i];
            }

            return new Binary(result);
        } else
            throw new Exception(Messages.Exceptions.sizeNegative);
    }

    /**
     * Переводит число в двоичный вид
     *
     * @param value значение для перевода
     * @return
     */
    private Binary ToBin(Long value) throws Exception {
        Boolean[] bits = new Boolean[Long.SIZE];
        for (int i = 0; i < Long.SIZE; i++)
            bits[i] = Boolean.FALSE;
        String str = Long.toBinaryString(value);
        int length = str.length();
        for (int i = 0; i < Long.SIZE - length; i++)
            str = "0" + str;
        for (int i = 0; i < str.length(); i++) {
            bits[i] = str.charAt(i) != '0';
        }
        return new Binary(bits);
    }


    /**
     * Переводит число из двоичного кода в десятичный
     *
     * @return
     */
    public int ToInt() throws Exception {
        return this.ToLong().intValue();
    }

    /**
     * Переводит число из двоичного кода в десятичный
     *
     * @return
     */
    public Long ToLong() throws Exception {
        Long result = 0L;
        for (int i = 0; i < this.bits.length; i++) {
            result = result << 1;
            result += Converter.Booleans.Convert(this.Get(i));
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Other">

    /**
     * Проверяет колчество единиц на четность
     *
     * @return true -если содержит четное число бит (единиц)
     * @throws Exception
     */
    public Boolean Parity() throws Exception {
        if (this.bits.length == 0)
            throw new Exception(Messages.Exceptions.binaryEmpty);
        Integer count = 0;
        for (int i = 0; i < this.bits.length; i++)
            if (this.bits[i] == true)
                count++;
        return count % 2 == 0;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.bits.length; i++) {
            if (i % 4 == 0 && i > 0)
                str += " ";
            str += Converter.Booleans.Convert(this.bits[i]);
        }
        return str;
    }

    public String toStringHex() throws Exception {
        String str = "";
        int length = this.bits.length;
        while (length % 4 != 0)
            length++;
        Binary binHex = this.ToNBit(length);
        for (int i = 0; i < this.bits.length; i += 4) {
            int value = new Binary(binHex.Get(i, 4)).ToInt();
            String strValues = "0123456789ABCDEF";
            if (value < 0 || value > 15)
                throw new Exception(Messages.Exceptions.valueIncorrect);
            str += strValues.charAt(value);
        }
        return str;
    }
    //</editor-fold>
}
