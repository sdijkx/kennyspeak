import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by steven on 30-04-16.
 */
public class KennyTest {

    @Test
    public void testEncode() {
        Kenny kenny = new Kenny();
        String encoded = kenny.encode("mmmmmpmmfmpmmppmpfmfmmfpmffpmmpmppmfppmpppppfpfmpfppfffmmfmpfmffpmfppfpfffmffp .");
        assertEquals("abcdefghijklmnopqrstuvwxyz .", encoded);
    }

    @Test(expected = IllegalStateException.class)
    public void testEncodeError() {
        Kenny kenny = new Kenny();
        String encoded = kenny.encode("mmA");
    }

    @Test(expected = IllegalStateException.class)
    public void testEncodeIllegalKennySpeak() {
        Kenny kenny = new Kenny();
        String encoded = kenny.encode("fff");
    }

    @Test
    public void testEncodeEmpty() {
        Kenny kenny = new Kenny();
        String encoded = kenny.encode("");
        assertEquals("",encoded);
    }

    @Test
    public void testDecode() {
        Kenny kenny = new Kenny();
        String decoded = kenny.decode("abcdefghijklmnopqrstuvwxyz .");
        assertEquals("mmmmmpmmfmpmmppmpfmfmmfpmffpmmpmppmfppmpppppfpfmpfppfffmmfmpfmffpmfppfpfffmffp .", decoded);

    }


}
