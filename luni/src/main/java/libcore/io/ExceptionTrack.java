/*
 *  @author Yigong Hu <hyigong1@cs.jhu.edu>
 *
 *  The LeaseOS Project
 *
 *  Copyright (c) 2018, Johns Hopkins University - Order Lab.
 *      All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package libcore.io;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 *
 */
public class ExceptionTrack {

    public static final int FIRST_APPLICATION_UID = 10000;
    public static final int LAST_APPLICATION_UID = 19999;
    private Hashtable<Integer, Integer> mExceptionTable = new Hashtable<>() ;
    private ArrayList<Integer> mExemptTable = new ArrayList<>();
    private static ExceptionTrack gInstance = null;


    public static ExceptionTrack getInstance() {
        if (gInstance == null) {
            gInstance = new ExceptionTrack();
        }
        return gInstance;
    }

    private ExceptionTrack() {
        System.out.println("Create a track");
    }


    public void noteException() {
        synchronized (this) {
            int uid = Libcore.os.getuid();
            System.out.println("Note Exception for uid " + uid);
            if (!isExempt(Libcore.os.getuid())) {
                Integer nException = mExceptionTable.get(uid);
                int exceptions;
                if (nException == null) {
                    exceptions = 0;
                } else {
                    exceptions = nException;
                }
                exceptions++;
                this.mExceptionTable.put(uid, exceptions);
                System.out.println("The number of Exceptions are " + exceptions);
            }
        }


    }

    public int getAndClearException(int uid) {
        System.out.println("Get the value. The length of Exception tables are  " + mExceptionTable.size());
        if (!mExceptionTable.containsKey(uid)) {
            return 0;
        }
        int nException = mExceptionTable.get(uid);
        System.out.println("The length of Exception tables are  " + mExceptionTable.size());
        System.out.println("The number of exceptions are " + nException + "for uid" + uid);
        return nException;
    }


    public boolean isExempt(int uid) {
        System.out.println("Check the exempt");
        synchronized (this) {
            if (uid < FIRST_APPLICATION_UID || uid > LAST_APPLICATION_UID) {
                return true;
            }
            System.out.println(this);
            System.out.println(mExemptTable);
            System.out.println("The length of exempt tables are  " + mExemptTable.size());
            return mExemptTable.contains(uid);
        }
    }


    public void setExempt() {
        System.out.println("Set the Exempt");
        synchronized (this) {
            /*(
            String fileName = "/data/system/exemptlist.txt";
            FileReader reader = null;
            try {
                reader = new FileReader(fileName);
            } catch (Exception e) {
                System.out.println("Can not read");
                e.printStackTrace();
            }
            try {
                int c = reader.read();
                String uid = "";

                System.out.print((char) c);
                while ((char) c != ']') {
                    if (c >= 48 && c <= 57) {
                        uid = uid + (char) c;
                        c = reader.read();
                    } else if ((char) c == ',') {
                        mExemptTable.add(Integer.valueOf(uid));
                        uid = "";
                        c = reader.read();
                    } else {
                        System.out.print((char) c);
                        c = reader.read();
                    }
                }
            } catch (Exception e) {
                System.out.println("No file");
                e.printStackTrace();
            }*/
            this.mExemptTable.addAll(
                    Arrays.asList(10033, 10068, 10059, 10027, 10002, 10053, 10040, 10001, 10003,
                            10088, 10052, 10022, 10008, 10013, 10067, 10073, 10076, 10066, 10081,
                            10004, 10024, 10054, 10045, 10036, 10020, 10079, 10034, 10029, 10063,
                            10074, 10057, 10005, 10048, 10072, 10028, 10078, 10071, 10016, 10058,
                            10070, 10015, 10077, 10056, 10025, 10086, 10084, 10087, 10047, 10060,
                            10032, 10014, 10017, 10035, 10043, 10011, 10083, 10009, 10080, 10061,
                            10019, 10031, 10018, 10075, 10082, 10042, 10044, 10055, 10050, 10023,
                            10064, 10046, 10007, 10041, 10030, 10038, 10039, 10085, 10069, 10010,
                            10065, 10049, 10021, 10012, 10037, 10051, 10000, 10006, 10062, 10026));
            System.out.println(this);
            System.out.println(mExemptTable);
            System.out.println("The length of exempt tables are " + mExemptTable.size());
        }
    }


}