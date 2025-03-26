import React, { useState } from "react";
import axios from "axios"; // Make sure axios is installed

function App() {
    const [htmlContent, setHtmlContent] = useState("");

    const fetchSchedule = async (type) => {
        try {
            const response = await axios.get(`http://localhost:8080/file?type=${type}`);
            const data = response.data;

            // Convert JSON to HTML table
            const tableRows = data.map(course => `
                <tr>
                    <td>${course.courseName}</td>
                    <td>${course.time}</td>
                    <td>${course.room}</td>
                </tr>
            `).join("");

            const html = `
                <table border="1" style="border-collapse: collapse; width: 100%;">
                    <thead>
                        <tr>
                            <th>Course</th>
                            <th>Time</th>
                            <th>Room</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tableRows}
                    </tbody>
                </table>
            `;

            setHtmlContent(html);
        } catch (err) {
            console.error("Failed to fetch schedule:", err);
            setHtmlContent(`<p style="color: red;">Error fetching data: ${err.message}</p>`);
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>PDF/Excel to HTML Converter (Live Data)</h2>
            <div style={{ marginBottom: "20px" }}>
                <button onClick={() => fetchSchedule("excel")} style={{ marginRight: "10px" }}>
                    Convert Excel
                </button>
                <button onClick={() => fetchSchedule("pdf")}>
                    Convert PDF
                </button>
            </div>

            <div style={{ border: "1px solid #ccc", padding: "10px" }}>
                <h4>Converted HTML:</h4>
                {htmlContent ? (
                    <div dangerouslySetInnerHTML={{ __html: htmlContent }} />
                ) : (
                    <p>No content yet. Click a button above to fetch conversion.</p>
                )}
            </div>
        </div>
    );
}

export default App;



