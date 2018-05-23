package pst.asu.beans.files;


import com.sun.faces.context.ExternalContextImpl;
import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


@ManagedBean(name = "dtDownload")
@Stateless
public class DownLoadBean {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // ..bytes = 10KB.
//    private static final String DEFAULT_DIRECTORY_PATH = "/opt/"; // /opt/   ! слэш на конце - обязателен!
    private static final String DEFAULT_DIRECTORY_PATH = "d:/4/video/"; // /opt/   ! слэш на конце - обязателен!

    public String id;
    public String filename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void init() {
        try {
            if (id == null) {
                return;
            }
            if (filename == null) {
                return;
            }
            if (id.equals("1")) {
                download2(filename);
            }
            if (id.equals("2")) {
//                download2(filename);
                downloadFace(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnFile(String filename, OutputStream out)
            throws FileNotFoundException, IOException {
        InputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(filename));
            byte[] buf = new byte[4 * 1024];  // 4K buffer
            int bytesRead;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void download2(String fileName) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.reset();

//        fileName = "111.mp4";
        File pdf = new File(DEFAULT_DIRECTORY_PATH + fileName);

        InputStream input = new BufferedInputStream(new FileInputStream(pdf));
        ServletOutputStream output = response.getOutputStream();

        response.setContentType("video/mp4");
//        response.addHeader("Accept-Ranges","bytes");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength((int) pdf.length());

        try {
            int read = 0;
            while ((read = input.read()) != -1) {
                output.write(read);
            }
        } finally {
            input.close();
            output.flush();
            output.close();
        }

        facesContext.responseComplete();
    }

    /**
     * Returns a substring of the given string value from the given begin index to the given end
     * index as a long. If the substring is empty, then -1 will be returned
     *
     * @param value      The string value to return a substring as long for.
     * @param beginIndex The begin index of the substring to be returned as long.
     * @param endIndex   The end index of the substring to be returned as long.
     * @return A substring of the given string value as long or -1 if substring is empty.
     */
    private static long sublong(String value, int beginIndex, int endIndex) {
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    /**
     * Copy the given byte range of the given input to the given output.
     *
     * @param input  The input to copy the given range to the given output for.
     * @param output The output to copy the given range from the given input for.
     * @param start  Start of the byte range.
     * @param length Length of the byte range.
     * @throws IOException If something fails at I/O level.
     */
    private static void copy(RandomAccessFile input, OutputStream output, long start, long length)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;

        if (input.length() == length) {
            // Write full range.
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } else {
            // Write partial range.
            input.seek(start);
            long toRead = length;

            while ((read = input.read(buffer)) > 0) {
                if ((toRead -= read) > 0) {
                    output.write(buffer, 0, read);
                } else {
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    /**
     * Close the given resource.
     *
     * @param resource The resource to be closed.
     */
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException ignore) {
                // Ignore IOException. If you want to handle this anyway, it might be useful to know
                // that this will generally only be thrown when the client aborted the request.
            }
        }
    }

    // Inner classes ------------------------------------------------------------------------------

    /**
     * This class represents a byte range.
     */
    protected class Range {
        long start;
        long end;
        long length;
        long total;

        /**
         * Construct a byte range.
         *
         * @param start Start of the byte range.
         * @param end   End of the byte range.
         * @param total Total length of the byte source.
         */
        public Range(long start, long end, long total) {
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
            this.total = total;
        }

    }

    public void downloadFace(String fileName) throws IOException {
        File file = new File(DEFAULT_DIRECTORY_PATH + fileName);
//        Faces.sendFile(file, true);
        Faces.sendFile(file, false);
    }

    public void download(String fileName) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.reset();

//        fileName = "111.mp4";
        fileName = "222.MOV";
        File file = new File("/opt/" + fileName);
        long length = file.length();

        String range = ((HttpServletRequest) ((ExternalContextImpl) externalContext).getRequest()).getHeader("Range");
        if (range != null) {

            // Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
            if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
                response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }
            int b = 0;
        }
        int a = 2;
        InputStream input = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream output = response.getOutputStream();

        response.setContentType("video/mp4");
        response.addHeader("Accept-Ranges", "bytes");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength((int) file.length());

        try {
            int read = 0;
            while ((read = input.read()) != -1) {
                output.write(read);
            }
        } finally {
            input.close();
            output.flush();
            output.close();
        }

        facesContext.responseComplete();
    }


}
