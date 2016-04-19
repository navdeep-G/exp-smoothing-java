package util;

import org.junit.Ignore;
import water.*;
import water.fvec.*;
import water.parser.ParseDataset;
import water.parser.ParseSetup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Ignore("Support for tests, but no actual tests here")
public class TestUtil extends Iced {
    private static boolean _stall_called_before = false;
    private static String[] ignoreTestsNames;
    private static String[] doonlyTestsNames;
    protected static int _initial_keycnt = 0;
    protected static int MINCLOUDSIZE;

    public TestUtil() { this(1); }
    public TestUtil(int minCloudSize) {
        MINCLOUDSIZE = Math.max(MINCLOUDSIZE,minCloudSize);
        String ignoreTests = System.getProperty("ignore.tests");
        if (ignoreTests != null) {
            ignoreTestsNames = ignoreTests.split(",");
            if (ignoreTestsNames.length == 1 && ignoreTestsNames[0].equals("")) {
                ignoreTestsNames = null;
            }
        }
        String doonlyTests = System.getProperty("doonly.tests");
        if (doonlyTests != null) {
            doonlyTestsNames = doonlyTests.split(",");
            if (doonlyTestsNames.length == 1 && doonlyTestsNames[0].equals("")) {
                doonlyTestsNames = null;
            }
        }
    }

    // ==== Test Setup & Teardown Utilities ====
    // Stall test until we see at least X members of the Cloud
    public static void stall_till_cloudsize(int x) {
        stall_till_cloudsize(new String[] {}, x);
    }
    public static void stall_till_cloudsize(String[] args, int x) {
        if( !_stall_called_before ) {
            H2O.main(args);
            H2O.registerRestApis(System.getProperty("user.dir"));
            _stall_called_before = true;
        }
        H2O.waitForCloudSize(x, 30000);
        _initial_keycnt = H2O.store_size();
    }

    // ==== Data Frame Creation Utilities ====

    /** Hunt for test files in likely places.  Null if cannot find.
     *  @param fname Test filename
     *  @return      Found file or null */
    protected static File find_test_file_static(String fname) {
        // When run from eclipse, the working directory is different.
        // Try pointing at another likely place
        File file = new File(fname);
        if( !file.exists() )
            file = new File("target/" + fname);
        if( !file.exists() )
            file = new File("../" + fname);
        if( !file.exists() )
            file = new File("../../" + fname);
        if( !file.exists() )
            file = new File("../target/" + fname);
        if( !file.exists() )
            file = null;
        return file;
    }

    /** Hunt for test files in likely places.  Null if cannot find.
     *  @param fname Test filename
     *  @return      Found file or null */
    protected File find_test_file(String fname) {
        return find_test_file_static(fname);
    }

    /** Find & parse a CSV file.  NPE if file not found.
     *  @param fname Test filename
     *  @return      Frame or NPE */
    public static Frame parse_test_file( String fname ) { return parse_test_file(Key.make(),fname); }
    public static Frame parse_test_file( Key outputKey, String fname) {
        File f = find_test_file_static(fname);
        assert f != null && f.exists():" file not found: " + fname;
        NFSFileVec nfs = NFSFileVec.make(f);
        return ParseDataset.parse(outputKey, nfs._key);
    }
    protected Frame parse_test_file( Key outputKey, String fname , boolean guessSetup) {
        File f = find_test_file(fname);
        assert f != null && f.exists():" file not found: " + fname;
        NFSFileVec nfs = NFSFileVec.make(f);
        return ParseDataset.parse(outputKey, new Key[]{nfs._key}, true, ParseSetup.guessSetup(new Key[]{nfs._key},false,1));
    }

    /** Find & parse a folder of CSV files.  NPE if file not found.
     *  @param fname Test filename
     *  @return      Frame or NPE */
    protected Frame parse_test_folder( String fname ) {
        File folder = find_test_file(fname);
        assert folder.isDirectory();
        File[] files = folder.listFiles();
        Arrays.sort(files);
        ArrayList<Key> keys = new ArrayList<Key>();
        for( File f : files )
            if( f.isFile() )
                keys.add(NFSFileVec.make(f)._key);
        Key[] res = new Key[keys.size()];
        keys.toArray(res);
        return ParseDataset.parse(Key.make(), res);
    }

    public static  Key makeByteVec(Key k, String... data) {
        byte [][] chunks = new byte[data.length][];
        long [] espc = new long[data.length+1];
        for(int i = 0; i < chunks.length; ++i){
            chunks[i] = data[i].getBytes();
            espc[i+1] = espc[i] + data[i].length();
        }
        Futures fs = new Futures();
        Key key = Vec.newKey();
        ByteVec bv = new ByteVec(key,Vec.ESPC.rowLayout(key,espc));
        for(int i = 0; i < chunks.length; ++i){
            Key chunkKey = bv.chunkKey(i);
            DKV.put(chunkKey, new Value(chunkKey,chunks[i].length,chunks[i],TypeMap.C1NCHUNK,Value.ICE),fs);
        }
        DKV.put(bv._key,bv,fs);
        Frame fr = new Frame(k,new String[]{"makeByteVec"},new Vec[]{bv});
        DKV.put(k, fr, fs);
        fs.blockForPending();
        return k;
    }
}
